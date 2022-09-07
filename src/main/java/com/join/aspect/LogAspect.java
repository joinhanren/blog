package com.join.aspect;

import com.alibaba.fastjson.JSON;
import com.join.annotation.LogAnnotation;
import com.join.utils.HttpContextUtils;
import com.join.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author join
 * @Description
 * @date 2022/9/5 22:44
 */
@Component
@Aspect
@Slf4j
public class LogAspect {


    /**
     * execution()：用于匹配是连接点的执行方法
     *
     * @annotation：限定匹配带有指定注解的连接点
     */
    @Pointcut("@annotation(com.join.annotation.LogAnnotation)")
//    @Pointcut("execution(* com.join.controller.ArticleController.haha(..))")
    public void pointCut(){}

    /**
     * 环绕通知
     */
    @Around("pointCut()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        /**
         * 切入前获取当前时间
         */
        Long beginTime = System.currentTimeMillis();

        /**
         * 继续执行原来的函数
         */
        Object result = joinPoint.proceed();

        /**
         * 切入后获取时间差，并且调用日志函数
         */
        Long time = System.currentTimeMillis() - beginTime;
        recordLog(joinPoint,time);
        return result;
    }

    /**
     * 显示日志函数
     * @param joinPoint
     * @param time
     */
    private void recordLog(ProceedingJoinPoint joinPoint,Long time){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        log.info("=========================log start=========================");
        log.info("module:{}",logAnnotation.module());
        log.info("operation:{}",logAnnotation.operation());

        /**
         * 请求的方法名
         */
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("request method:{}",className+"."+methodName+"()");

        /**
         * 请求的参数
         */
        Object[] args = joinPoint.getArgs();
        if (args==null){
            log.info("params:{}","参数为空");
        }
        else {
            String params = JSON.toJSONString(args);
            log.info("params:{}",params);
        }


        /**
         * 获取request 设置ip地址
         */
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        log.info("ip {}", IPUtils.getIpAddr(request));

        log.info("execute time : {} ms",time);
        log.info("=========================log end=========================");
    }
}
