package com.bai.file.service;

import com.bai.file.Iinterface.IStorageService;
import com.bai.file.domain.StorageBean;
import com.bai.file.domain.SysParam;
import com.bai.file.mapper.StorageMapper;
import com.bai.file.mapper.SysParamMapper;
import com.bai.file.mapper.UserFileMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Slf4j
@Service
@Transactional(rollbackFor=Exception.class)
public class StorageService extends ServiceImpl<StorageMapper, StorageBean> implements IStorageService {
    @Resource
    StorageMapper storageMapper;
    @Resource
    SysParamMapper sysParamMapper;
    @Resource
    UserFileMapper userFileMapper;

    public Long getTotalStorageSize(String userId) {
        LambdaQueryWrapper<StorageBean> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StorageBean::getUserId, userId);

        StorageBean storageBean = storageMapper.selectOne(lambdaQueryWrapper);
        Long totalStorageSize = null;
        if (storageBean == null || storageBean.getTotalStorageSize() == null) {
            LambdaQueryWrapper<SysParam> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
            lambdaQueryWrapper1.eq(SysParam::getSysParamKey, "totalStorageSize");
            SysParam sysParam = sysParamMapper.selectOne(lambdaQueryWrapper1);
            totalStorageSize = Long.parseLong(sysParam.getSysParamValue());
            storageBean = new StorageBean();
            storageBean.setUserId(userId);
            storageBean.setTotalStorageSize(totalStorageSize);
            storageMapper.insert(storageBean);
        } else  {
            totalStorageSize = storageBean.getTotalStorageSize();
        }

        if (totalStorageSize != null) {
            totalStorageSize = totalStorageSize * 1024 * 1024;
        }
        return totalStorageSize;
    }

    public boolean checkStorage(String userId, Long fileSize) {
        LambdaQueryWrapper<StorageBean> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StorageBean::getUserId, userId);

        StorageBean storageBean = storageMapper.selectOne(lambdaQueryWrapper);
        Long totalStorageSize = null;
        if (storageBean == null || storageBean.getTotalStorageSize() == null) {
            LambdaQueryWrapper<SysParam> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
            lambdaQueryWrapper1.eq(SysParam::getSysParamKey, "totalStorageSize");
            SysParam sysParam = sysParamMapper.selectOne(lambdaQueryWrapper1);
            totalStorageSize = Long.parseLong(sysParam.getSysParamValue());
            storageBean = new StorageBean();
            storageBean.setUserId(userId);
            storageBean.setTotalStorageSize(totalStorageSize);
            storageMapper.insert(storageBean);
        } else  {
            totalStorageSize = storageBean.getTotalStorageSize();
        }

        if (totalStorageSize != null) {
            totalStorageSize = totalStorageSize * 1024 * 1024;
        }

        Long storageSize = userFileMapper.selectStorageSizeByUserId(userId);
        if (storageSize == null ){
            storageSize = 0L;
        }
        if (storageSize + fileSize > totalStorageSize) {
            return false;
        }
        return true;

    }
}
