package com.bai.file.api;

import com.bai.file.domain.ShareFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bai.file.vo.share.ShareFileListVO;

import java.util.List;

public interface IShareFileService extends IService<ShareFile> {

    List<ShareFileListVO> selectShareFileList(String shareBatchNum, String filePath);
}
