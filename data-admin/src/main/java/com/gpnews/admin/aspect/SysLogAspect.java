package com.gpnews.admin.aspect;

import com.alibaba.druid.support.json.JSONUtils;
import com.gpnews.admin.annotation.SystemLog;
import com.gpnews.admin.service.SysLogService;
import com.gpnews.pojo.SysLog;
import com.gpnews.utils.IpUtils;
import com.gpnews.utils.JsonUtil;
import com.gpnews.utils.ShiroUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author HuangChongHeng
 * @date 2020/3/17
 */
@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private SysLogService service;

    @Pointcut("@annotation(com.gpnews.admin.annotation.SystemLog)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object result = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //异步保存日志
        saveLog(point, time);
        return result;
    }

    void saveLog(ProceedingJoinPoint joinPoint, long time) throws InterruptedException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLog sysLog = new SysLog();
        SystemLog systemLog = method.getAnnotation(SystemLog.class);
        if (systemLog != null) {
            // 注解上的描述
            sysLog.setOperation(systemLog.value());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        // 请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = JsonUtil.serialize(args[0]);
            sysLog.setParams(params);
        } catch (Exception ignored) {
        }
        // 设置IP地址
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        sysLog.setIp(IpUtils.getClientIp(request));
        // 用户名
        String currUserId = ShiroUtil.getCurrUserId();
        sysLog.setUserId(currUserId);
        sysLog.setTime((int) time);
        // 系统当前时间
        Date date = new Date();
        // 保存系统日志
        service.insert(sysLog);
    }
}
