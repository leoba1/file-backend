package com.bai.file.api;

import com.bai.file.domain.Notice;
import com.bai.file.dto.notice.NoticeListDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface INoticeService extends IService<Notice> {


    IPage<Notice> selectUserPage(NoticeListDTO noticeListDTO);

}
