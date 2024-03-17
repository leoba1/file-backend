package com.bai.file.config.security.filter;

import com.qiwenshare.common.exception.NotLoginException;
import com.qiwenshare.common.exception.QiwenException;
import com.bai.file.service.SysParamService;
import com.bai.file.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Jwt过滤器（第一个过滤器）：获取用户token，查询用户信息拼装到security中，以便后续filter使用
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private String[] ignoreUri = {"/user/register",
            "/user/login",
            "/user/checkuserlogininfo",
            "/filetransfer/downloadfile",
            "/filetransfer/batchDownloadFile",
            "/filetransfer/preview",
            "/share/sharefileList",
            "/share/sharetype",
            "/share/checkextractioncode",
            "/share/checkendtime",
            "/notice/list",
            "/notice/detail",
            "/param/grouplist",
            "/v3/api-docs",
            "/swagger-ui.html",
            "/office/IndexServlet",
            "/temp/upload",
            "/temp/*",
            "/temp/download/"
    };
    private String[] antWhiteUri = {"/*.html",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js",
            "/swagger-ui/**",
            "/webSocket/**",
            "/error/**",};
    @Autowired
    private UserService userService;
    @Resource
    SysParamService sysParamService;

    @Value("${qiwen.file.version}")
    String qiwenVersion;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String version = sysParamService.getValue("version");
        if (!qiwenVersion.equals(version)) {
            throw new QiwenException(999999, "脚本未初始化，请在数据库执行数据初始化脚本，存放路径： '/resources/import.sql'！");
        }
        List<String> antWhiteUriList = Arrays.asList(antWhiteUri);
        for (String antWhiteUri : antWhiteUriList) {
            AntPathRequestMatcher regexRequestMatcher = new AntPathRequestMatcher(antWhiteUri, "GET", true);
            if (regexRequestMatcher.matches(request)) {
                chain.doFilter(request, response);
                return;
            }
        }

        for (String uri : ignoreUri) {
            // 将*转换为.，并且使用^和$确保只匹配完整的URI
            String regex = uri.replace("*", ".*");
            if (Pattern.matches(regex, request.getRequestURI())) {
                chain.doFilter(request, response);
                return;
            }
        }
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            throw new NotLoginException("用户未登录");
        } else {
            String userId = userService.getUserIdByToken(token);
            if (userId == null) {
                throw new NotLoginException("用户未登录");
            }
            // 验证
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(String.valueOf(userId));
                if (userDetails.isEnabled()) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        chain.doFilter(request, response);
    }

}
