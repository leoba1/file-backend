package com.bai.file.service;

import com.bai.file.mapper.TempFileMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bai.file.api.ITempFileService;
import com.bai.file.dto.file.TempFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(rollbackFor=Exception.class)
public class TempFileService extends ServiceImpl<TempFileMapper, TempFile> implements ITempFileService {

}
