package com.bai.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bai.file.domain.RecoveryFile;
import com.bai.file.vo.file.RecoveryFileListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RecoveryFileMapper extends BaseMapper<RecoveryFile> {
    List<RecoveryFileListVo> selectRecoveryFileList(@Param("userId") String userId);
}
