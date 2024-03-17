package com.bai.file.api;


import com.bai.file.domain.OperationLogBean;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface IOperationLogService  extends IService<OperationLogBean> {
    IPage<OperationLogBean> selectOperationLogPage(Integer current, Integer size);

    List<OperationLogBean> selectOperationLog();

    void insertOperationLog(OperationLogBean operationlogBean);
}
