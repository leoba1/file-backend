package com.bai.file.Iinterface;

import com.bai.file.domain.StorageBean;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IStorageService extends IService<StorageBean> {
    Long getTotalStorageSize(String userId);
    boolean checkStorage(String userId, Long fileSize);
}
