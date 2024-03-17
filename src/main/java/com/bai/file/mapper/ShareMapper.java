package com.bai.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bai.file.domain.Share;
import com.bai.file.vo.share.ShareListVO;

import java.util.List;

public interface ShareMapper  extends BaseMapper<Share> {

    List<ShareListVO> selectShareList(String shareFilePath,String shareBatchNum, Long beginCount, Long pageCount, String userId);
    int selectShareListTotalCount(String shareFilePath,String shareBatchNum, String userId);
}
