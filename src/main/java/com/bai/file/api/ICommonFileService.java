package com.bai.file.api;

import com.bai.file.domain.CommonFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bai.file.vo.commonfile.CommonFileListVo;
import com.bai.file.vo.commonfile.CommonFileUser;

import java.util.List;

public interface ICommonFileService extends IService<CommonFile> {
    List<CommonFileUser> selectCommonFileUser(String userId);
    List<CommonFileListVo> selectCommonFileByUser(String userId, String sessionUserId);
}
