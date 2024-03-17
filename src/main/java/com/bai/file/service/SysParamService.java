package com.bai.file.service;

import com.bai.file.api.ISysParamService;
import com.bai.file.domain.SysParam;
import com.bai.file.mapper.SysParamMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author MAC
 * @version 1.0
 * @description: TODO
 * @date 2021/12/30 14:54
 */
@Slf4j
@Service
public class SysParamService extends ServiceImpl<SysParamMapper, SysParam> implements ISysParamService {

    @Resource
    SysParamMapper sysParamMapper;

    @Override
    public String getValue(String key) {
        SysParam sysParam = new SysParam();
        sysParam.setSysParamKey(key);
        List<SysParam> list = sysParamMapper.selectList(new QueryWrapper<>(sysParam));
        if (list != null && !list.isEmpty()) {
            return list.get(0).getSysParamValue();
        }
        return null;
    }
}
