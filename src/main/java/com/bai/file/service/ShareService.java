package com.bai.file.service;

import com.bai.file.api.IShareService;
import com.bai.file.domain.Share;
import com.bai.file.dto.sharefile.ShareListDTO;
import com.bai.file.mapper.ShareMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bai.file.vo.share.ShareListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Slf4j
@Service
@Transactional(rollbackFor=Exception.class)
public class ShareService extends ServiceImpl<ShareMapper, Share> implements IShareService {

    @Resource
    ShareMapper shareMapper;

    @Override
    public List<ShareListVO> selectShareList(ShareListDTO shareListDTO, String userId) {
        Long beginCount = (shareListDTO.getCurrentPage() - 1) * shareListDTO.getPageCount();
        return shareMapper.selectShareList(shareListDTO.getShareFilePath(),
                shareListDTO.getShareBatchNum(),
                beginCount, shareListDTO.getPageCount(), userId);
    }

    @Override
    public int selectShareListTotalCount(ShareListDTO shareListDTO, String userId) {
        return shareMapper.selectShareListTotalCount(shareListDTO.getShareFilePath(), shareListDTO.getShareBatchNum(), userId);
    }
}
