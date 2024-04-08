package com.bai.file.Iinterface;

import com.bai.file.domain.SysParam;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author MAC
 * @version 1.0
 * @description: TODO
 * @date 2021/12/30 14:54
 */
public interface ISysParamService  extends IService<SysParam> {
    String getValue(String key);
}
