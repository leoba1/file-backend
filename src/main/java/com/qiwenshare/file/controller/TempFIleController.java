package com.qiwenshare.file.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.qiwenshare.file.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/temp")
@CrossOrigin
@Slf4j
public class TempFIleController {

    @Value("${temp.file.path}")
    private String path;

    /**
     * 上传接口
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public Result<?> upload(@RequestBody MultipartFile file) throws IOException {

        //生成随机数
        String randomCode = RandomUtil.randomString(RandomUtil.BASE_CHAR_NUMBER, 6);

        //获取原文件的名称
        String originalFilename = file.getOriginalFilename();

        //文件路径
        String property = System.getProperty("user.dir");
        String rootFilePath =property+"/"+path+"/"+randomCode+"/"+originalFilename;
        FileUtil.writeBytes(file.getBytes(),rootFilePath);
        return Result.success(randomCode);//需要返回 地址+filename
    }

    /**
     * 下载接口
     * @param fileName 文件名
     * @param response 直接响应
     * @throws IOException
     */
    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException{
        final Path path = Paths.get(System.getProperty("user.dir") + "/springboot/src/main/resources/files/"+fileName);
//        设置响应头
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
//        获取输出流
        final WritableByteChannel writableByteChannel = Channels.newChannel(response.getOutputStream());
//        读取文件流通道
        final FileChannel fileChannel = new FileInputStream(path.toFile()).getChannel();
//        写入数据到通道
        fileChannel.transferTo(0, fileChannel.size(), writableByteChannel);
        fileChannel.close();
        writableByteChannel.close();

    }
}
