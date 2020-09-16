package com.xh.controller.sys;

import com.xh.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class LogController {
    @Autowired
    private LogService logService;

}
