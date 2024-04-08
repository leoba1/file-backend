package com.bai.file.service;

import com.bai.file.Iinterface.IUserLoginInfoService;
import com.bai.file.domain.UserLoginInfo;
import com.bai.file.mapper.UserLoginInfoMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional(rollbackFor=Exception.class)
public class UserLoginInfoService extends ServiceImpl<UserLoginInfoMapper, UserLoginInfo> implements IUserLoginInfoService {


}
