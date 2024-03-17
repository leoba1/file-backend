package com.bai.file.service;

import com.bai.file.api.IUploadTaskService;
import com.bai.file.domain.UploadTask;
import com.bai.file.mapper.UploadTaskMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class UploadTaskService extends ServiceImpl<UploadTaskMapper, UploadTask> implements IUploadTaskService {


}
