package com.bai.file.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.bai.file.mapper.TempFileMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bai.file.dto.file.TempFile;
import com.bai.file.service.TempFileService;
import com.bai.file.util.Result;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlRunner;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/temp")
@CrossOrigin
@Slf4j
public class TempFIleController {

    @Value("${temp.file.path}")
    private String localPath;

    @Autowired
    private TempFileService tempFileService;

    @Autowired
    private TempFileMapper tempFileMapper;

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
            return Result.error("500","文件大小不合法");
        }

        //生成随机数
        String randomCode = null;
        String ranCode = "QWERTYUIOPASDFGHJKLZXCVBNMabcdefghijklmnopqrstuvwxyz0123456789";
        randomCode=RandomUtil.randomString(ranCode, 6);

        QueryWrapper<TempFile> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("code",randomCode);
        TempFile one = tempFileService.getOne(queryWrapper);
        if (one != null){
            randomCode =RandomUtil.randomString(ranCode, 6);
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

        if (code == null){
            return;
        }
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

    /**
     * 定时删除
     * @param
     * @param
     * @throws
     */
    @Scheduled(cron = "0 0 4 * * ?")
    public void deleteDir(){

        File directory = new File(localPath);

        if (!directory.exists()) {
            System.out.println("Directory does not exist.");
            return;
        }
        QueryWrapper<TempFile> queryWrapper =new QueryWrapper<>();
        queryWrapper.notIn("code","eeeee");
        tempFileMapper.delete(queryWrapper);
        try {
            delete(directory);
            System.out.println("Directory is deleted.");
        } catch (Exception e) {
            System.out.println("Failed to delete directory.");
            e.printStackTrace();
        }
    }
    private void delete(File file) throws Exception {
        if (file.isDirectory()) {
            //如果是目录，递归删除目录下的所有文件和文件夹
            for (File subFile : file.listFiles()) {
                delete(subFile);
            }
        }
        file.delete(); //删除文件或空目录
    }
}
