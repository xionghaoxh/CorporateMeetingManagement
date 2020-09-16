package com.xh.service;

import com.xh.entity.Sys_log;
import com.baomidou.mybatisplus.extension.service.IService;

public interface LogService extends IService<Sys_log> {
    /**
     * 新增日志
     *
     * @param log 日志实体信息
     * @return boolean
     */
    boolean addLog(Sys_log log);
}
