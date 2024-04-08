package com.bai.file.Iinterface;

import com.bai.file.domain.FileBean;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bai.file.vo.file.FileDetailVO;

public interface IFileService  extends IService<FileBean> {

    Long getFilePointCount(String fileId);
    void unzipFile(String userFileId, int unzipMode, String filePath);

    void updateFileDetail(String userFileId, String identifier, long fileSize);

    FileDetailVO getFileDetail(String userFileId);

}
