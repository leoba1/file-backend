package com.bai.file.service;

import com.bai.file.domain.OperationLogBean;
import com.bai.file.mapper.OperationLogMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bai.file.Iinterface.IOperationLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional(rollbackFor=Exception.class)
public class OperationLogService  extends ServiceImpl<OperationLogMapper, OperationLogBean> implements IOperationLogService {

    @Resource
    OperationLogMapper operationLogMapper;

    @Override
    public IPage<OperationLogBean> selectOperationLogPage(Integer current, Integer size) {
        IPage<OperationLogBean> page = new Page<>(current, size);
        IPage<OperationLogBean> list = operationLogMapper.selectPage(page, null);
        return list;
    }

    @Override
    public List<OperationLogBean> selectOperationLog() {
        List<OperationLogBean> result = operationLogMapper.selectList(null);
        return result;
    }

    @Override
    public void insertOperationLog(OperationLogBean operationlogBean) {
        operationLogMapper.insert(operationlogBean);

    }


}
