package com.baizhi.aop;

import com.baizhi.annotation.RedisCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

//未完，待续
//@Configuration //配置
//@Aspect  //切面通知
public class RedisTemplate {
    @Autowired
    private RedisTemplate redisTemplate;

    @Around("execution(* com.baizhi.service.serviceImpl.*.queryAll(..))")
    public Object around(ProceedingJoinPoint point) {
        return null;
    }

}
