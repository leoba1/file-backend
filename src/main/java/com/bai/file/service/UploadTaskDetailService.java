package com.bai.file.service;

import com.bai.file.api.IUploadTaskDetailService;
import com.bai.file.domain.UploadTaskDetail;
import com.bai.file.mapper.UploadTaskDetailMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UploadTaskDetailService extends ServiceImpl<UploadTaskDetailMapper, UploadTaskDetail> implements IUploadTaskDetailService {

    @Resource
    UploadTaskDetailMapper uploadTaskDetailMapper;

    @Override
    public List<Integer> getUploadedChunkNumList(String identifier) {
        return uploadTaskDetailMapper.selectUploadedChunkNumList(identifier);
    }
}
