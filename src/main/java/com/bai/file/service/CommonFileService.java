package com.bai.file.service;

import com.bai.file.Iinterface.ICommonFileService;
import com.bai.file.domain.CommonFile;
import com.bai.file.mapper.CommonFileMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bai.file.vo.commonfile.CommonFileListVo;
import com.bai.file.vo.commonfile.CommonFileUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
@Transactional(rollbackFor=Exception.class)
public class CommonFileService extends ServiceImpl<CommonFileMapper, CommonFile> implements ICommonFileService {

    @Resource
    CommonFileMapper commonFileMapper;

    @Override
    public List<CommonFileUser> selectCommonFileUser(String userId) {
        return commonFileMapper.selectCommonFileUser(userId);
    }

    @Override
    public List<CommonFileListVo> selectCommonFileByUser(String userId, String sessionUserId) {
        return commonFileMapper.selectCommonFileByUser(userId, sessionUserId);
    }


}
