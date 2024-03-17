package com.bai.file.api;

import com.bai.file.domain.RecoveryFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bai.file.vo.file.RecoveryFileListVo;

import java.util.List;

public interface IRecoveryFileService extends IService<RecoveryFile> {
    void deleteUserFileByDeleteBatchNum(String deleteBatchNum);
    void restorefile(String deleteBatchNum, String filePath, String sessionUserId);
    List<RecoveryFileListVo> selectRecoveryFileList(String userId);
}
