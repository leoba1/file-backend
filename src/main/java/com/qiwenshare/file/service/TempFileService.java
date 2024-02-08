package com.qiwenshare.file.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qiwenshare.file.api.ITempFileService;
import com.qiwenshare.file.dto.file.TempFile;
import com.qiwenshare.file.mapper.TempFileMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(rollbackFor=Exception.class)
public class TempFileService extends ServiceImpl<TempFileMapper, TempFile> implements ITempFileService {

}
