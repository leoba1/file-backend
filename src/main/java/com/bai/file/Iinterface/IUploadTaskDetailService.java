package com.bai.file.Iinterface;

import com.bai.file.domain.UploadTaskDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IUploadTaskDetailService  extends IService<UploadTaskDetail> {
    List<Integer> getUploadedChunkNumList(String identifier);
}
