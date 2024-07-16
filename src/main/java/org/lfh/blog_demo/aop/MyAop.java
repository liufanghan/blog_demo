package org.lfh.blog_demo.aop;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;


@Order(1)
@Aspect
@Component
@Slf4j
public class MyAop {

    @Pointcut("execution(* org.lfh.blog_demo.controller.*.*(..))")
    public void classLog() {
    }

    @Around("classLog()")
    public Object around(ProceedingJoinPoint jp) throws Throwable {
        // 方法执行前
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        System.out.println("URL : " + request.getRequestURL().toString());
        System.out.println("HTTP_METHOD : " + request.getMethod());
        System.out.println("CLASS_METHOD : " + jp);
        System.out.println("ARGS : " + Arrays.toString(jp.getArgs()));

        MethodSignature methodSignature = (MethodSignature) jp.getSignature();

        // 执行目标方法
        Object result;
        try {
            result = jp.proceed();  // 继续执行目标方法
            System.out.println("返回通知：方法的返回值 : " + result);
        } catch (Throwable throwable) {
            // 异常处理逻辑
            System.out.println("异常通知：方法异常时执行.....");
            System.out.println("产生异常的方法：" + jp);
            System.out.println("异常种类：" + throwable);
            throw throwable;  // 重新抛出异常
        }

        // 方法执行后
        System.out.println("后置通知：最后且一定执行.....");
        // 返回结果
        return result;
    }
}

