package com.atguigu.spzx.common.log.service;

import com.atguigu.spzx.model.entity.system.SysOperLog;

/**
 * @author xiaozhen
 * @date 2023/10/24
 */
public interface AsyncOperLogService {
    public void save(SysOperLog sysOperLog);
}
