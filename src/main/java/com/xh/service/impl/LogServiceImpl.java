package com.xh.service.impl;

import com.xh.entity.Sys_log;
import com.xh.mapper.LogMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xh.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Sys_log> implements LogService {
    @Autowired
    private LogMapper logMapper;

    @Override
    public boolean addLog(Sys_log log) {
        int flag = logMapper.insert(log);
        return flag > 0;
    }
}
