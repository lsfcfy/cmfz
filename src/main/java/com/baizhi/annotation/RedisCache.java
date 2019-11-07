package com.baizhi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 1 此处自定义Redis缓存
 * 2 配置切面
 */
@Target(ElementType.METHOD) //指定当前注解在哪个位置生效  这里是在方法上
@Retention(RetentionPolicy.RUNTIME) //指定当前注解运行时机，此处运行时生效
public @interface RedisCache {
}
