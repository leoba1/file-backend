package com.bai.file.service;

import com.bai.file.domain.FilePermission;
import com.bai.file.mapper.FilePermissionMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bai.file.api.IFilePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(rollbackFor=Exception.class)
public class FilePermissionService extends ServiceImpl<FilePermissionMapper, FilePermission> implements IFilePermissionService {

}
