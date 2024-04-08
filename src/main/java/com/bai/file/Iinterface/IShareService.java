package com.bai.file.Iinterface;

import com.bai.file.domain.Share;
import com.bai.file.dto.sharefile.ShareListDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bai.file.vo.share.ShareListVO;

import java.util.List;

public interface IShareService  extends IService<Share> {
    List<ShareListVO> selectShareList(ShareListDTO shareListDTO, String userId);
    int selectShareListTotalCount(ShareListDTO shareListDTO, String userId);
}
