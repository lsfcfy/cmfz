package com.baizhi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 需开启Redis虚拟机
 *《1》用jedis时，不用yal里配置redis端口 ip 但是需要工厂类里定义方法给定端口 ip
 *《2》用RedisTemplate时,yal配置rides端口
 * 1 此处自定义Redis缓存
 * 2 配置切面
 */
@Target(ElementType.METHOD) //指定当前注解在哪个位置生效  这里是在方法上
@Retention(RetentionPolicy.RUNTIME) //指定当前注解运行时机，此处运行时生效
public @interface RedisCache {
}
