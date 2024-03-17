package com.bai.file.service;

import com.bai.file.api.INoticeService;
import com.bai.file.domain.Notice;
import com.bai.file.dto.notice.NoticeListDTO;
import com.bai.file.mapper.NoticeMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class NoticeService extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {
    @Resource
    NoticeMapper noticeMapper;


    @Override
    public IPage<Notice> selectUserPage(NoticeListDTO noticeListDTO) {
        Page<Notice> page = new Page<>(noticeListDTO.getPage(), noticeListDTO.getPageSize());
        return noticeMapper.selectPageVo(page, noticeListDTO);
    }
}
