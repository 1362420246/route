

package com.qbk.aspect;

import com.alibaba.fastjson.JSON;
import com.qbk.annotation.SysLog;
import com.qbk.util.IPUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 系统日志，切面处理类
 */
@Aspect
@Component
public class SysLogAspect {

    /**
     * 定义切点，并对切点做一些增强操作：前置增强、环绕增强、后置增强等等，切点的定义我们可以在一个空方法体的方法上使用@Pointcut注解
     */
    @Pointcut("@annotation(com.qbk.annotation.SysLog)")
    public void logPointCut() {
    }

    /**
     * 环绕增强
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;

        //保存日志
        saveSysLog(point, time);

        return result;
    }

    /**
     * 保存日志
     * @param joinPoint
     * @param time
     */
    private void saveSysLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLog sysLog = method.getAnnotation(SysLog.class);
        if(sysLog != null){
            //注解上的描述
            System.out.println("日志描述:"+sysLog.value());
        }
        //请求的 类名、方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        System.out.println("类和方法:"+className + "." + methodName + "()");

        //请求的参数
        Object[] args = joinPoint.getArgs();
        try {
            String params = JSON.toJSONString(args[0]);
            System.out.println("参数："+params);
        } catch (Exception e) {

        }

        //获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //设置IP地址
        System.out.println("ip:"+IPUtils.getIpAddr(request));

        //用户名
//        Optional<String> currentUsername = SecurityUtils.getCurrentUsername();
//        if(currentUsername.isPresent()) {
//            String username = currentUsername.get();
//            System.out.println(username);
//        }
        System.out.println("用时:"+time);
        System.out.println("当前时间:"+new Date());
        //保存系统日志

    }
}
