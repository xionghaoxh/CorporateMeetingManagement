package com.xh.annotation;
import com.xh.common.utils.DateUtil;
import com.xh.common.utils.StringUtil;
import com.xh.entity.Sys_User;
import com.xh.entity.Sys_log;
import com.xh.service.LogService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 自定义注解方式记录日志到数据库
 */
@Aspect
@Component
public class SlogAspect {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private LogService logService;

    @Pointcut("@annotation(com.xh.annotation.SLog)")
    public void log() {

    }

    @Around("log()")
    public Object slog(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        String url = request.getRequestURI();
        SLog annotation = method.getAnnotation(SLog.class);
        Subject subject = SecurityUtils.getSubject();
        Sys_User user = (Sys_User) subject.getPrincipal();
        String time = DateUtil.getDateTime();
        String username = user.getUsername();
        String nickname = user.getNickname();
        String ip = StringUtil.getRemoteAddr(request);
        String operate = annotation.operate();
        String name = signature.getName();
        String _method = method.getName();
        Sys_log log = new Sys_log(null, username, nickname, ip, time, url, _method, operate);
        logService.addLog(log);
        return point.proceed();
    }

}
