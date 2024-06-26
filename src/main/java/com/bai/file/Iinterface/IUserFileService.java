package com.bai.file.Iinterface;

import com.bai.file.domain.UserFile;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bai.file.vo.file.FileListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IUserFileService extends IService<UserFile> {
    List<UserFile> selectUserFileByNameAndPath(String fileName, String filePath, String userId);
    List<UserFile> selectSameUserFile(String fileName, String filePath, String extendName, String userId);

    IPage<FileListVO> userFileList(String userId, String filePath, Long beginCount, Long pageCount);
    void updateFilepathByUserFileId(String userFileId, String newfilePath, String userId);
    void userFileCopy(String userId, String userFileId, String newfilePath);

    IPage<FileListVO> getFileByFileType(Integer fileTypeId, Long currentPage, Long pageCount, String userId);
    List<UserFile> selectUserFileListByPath(String filePath, String userId);
    List<UserFile> selectFilePathTreeByUserId(String userId);
    void deleteUserFile(String userFileId, String sessionUserId);

    List<UserFile> selectUserFileByLikeRightFilePath(@Param("filePath") String filePath, @Param("userId") String userId);

}
