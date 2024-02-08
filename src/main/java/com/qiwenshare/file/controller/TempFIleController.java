package com.qiwenshare.file.controller;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qiwenshare.file.dto.file.TempFile;
import com.qiwenshare.file.service.TempFileService;
import com.qiwenshare.file.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
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
import java.util.Date;

@RestController
@RequestMapping("/temp")
@CrossOrigin
@Slf4j
public class TempFIleController {

    @Value("${temp.file.path}")
    private String localPath;

    @Autowired
    private TempFileService tempFileService;

    /**
     * 上传接口
     * @param file
     * @return
     * @throws IOException
     */
    @Transactional(rollbackFor=Exception.class)
    @PostMapping("/upload")
    public Result<?> upload(@RequestBody MultipartFile file) throws IOException {
        long size = file.getSize();
        if (size > 1024 * 1024 * 20){
            throw new IOException();
        }

        //生成随机数
        String randomCode = null;
        randomCode=RandomUtil.randomString(RandomUtil.BASE_CHAR_NUMBER, 6);

        QueryWrapper<TempFile> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("code",randomCode);
        TempFile one = tempFileService.getOne(queryWrapper);
        if (one != null){
            randomCode =RandomUtil.randomString(RandomUtil.BASE_CHAR_NUMBER, 6);
        }

        //获取原文件的名称
        String originalFilename = file.getOriginalFilename();

        //保存信息
        TempFile tempFile=new TempFile();
        tempFile.setCode(randomCode);
        tempFile.setFileName(originalFilename);
        tempFile.setTime(DateUtil.date());
        tempFileService.save(tempFile);


        //文件路径
        String property = System.getProperty("user.dir");
        String rootFilePath =property+"/"+localPath+"/"+randomCode+"/"+originalFilename;
        FileUtil.writeBytes(file.getBytes(),rootFilePath);
        return Result.success(randomCode);//需要返回 地址+filename
    }

    /**
     * 下载接口
     * @param code 文件名
     * @param response 直接响应
     * @throws IOException
     */
    @Transactional(rollbackFor=Exception.class)
    @GetMapping("/download/{code}")
    public void download(@PathVariable String code, HttpServletResponse response) throws IOException{

        QueryWrapper<TempFile> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("code",code);
        TempFile one = tempFileService.getOne(queryWrapper);
        String fileName = one.getFileName();

        final Path path = Paths.get(System.getProperty("user.dir") +"/"+localPath+"/"+code+"/"+fileName);
        //设置响应头
        response.setContentType("application/octet-stream");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

        //获取输出流
        final WritableByteChannel writableByteChannel = Channels.newChannel(response.getOutputStream());
        //读取文件流通道
        final FileChannel fileChannel = new FileInputStream(path.toFile()).getChannel();
        //写入数据到通道
        fileChannel.transferTo(0, fileChannel.size(), writableByteChannel);
        fileChannel.close();
        writableByteChannel.close();

    }
    @GetMapping("/search/{code}")
    public Result<?> search(@PathVariable String code, HttpServletResponse response) throws IOException{
        QueryWrapper<TempFile> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("code",code);
        TempFile one = tempFileService.getOne(queryWrapper);

        if (one == null){
            return Result.error("1","文件不存在");
        }
        return Result.success(one);
    }
}
