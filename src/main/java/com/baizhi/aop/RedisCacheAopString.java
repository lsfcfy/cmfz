package com.baizhi.aop;

import com.alibaba.fastjson.JSONObject;
import com.baizhi.annotation.ClearRedisCache;
import com.baizhi.annotation.RedisCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * 自定义注解后  配置通知使注解生效
 * 此处用Redis的String类型
 * 弊端 大型项目挨个查询效率非常低
 */

//由于都是测试缓存 此处在测试RedisCacheAopHash 时先将注解注释
//@Configuration  //配置
//@Aspect //切面通知
public class RedisCacheAopString {
    @Autowired
    private Jedis jedis;

    //查询所有 配置环绕通知  第一个星代表返回值类型
    @Around("execution(* com.baizhi.service.serviceImpl.*.queryAll(..))")
    //因为返回值是所有类型 所以用object
    public Object around(ProceedingJoinPoint point) throws Throwable {
        // 判断方法上是否有RedisCache
        // 如果有，则需要缓存
        // 如果没有，直接方法放行

        //获取目标方法所在的 类对象
        //target:com.baizhi.service.impl.BannerServiceImpl@193b3b18
        Object target = point.getTarget();
        //获取目标方法 Map com.baizhi.service.impl.BannerServiceImpl.selectAll(Integer,Integer)
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        //获取目标方法的参数值
        Object[] args = point.getArgs();

        Method method = methodSignature.getMethod();
        //判断是否有rediscache注解
        boolean b = method.isAnnotationPresent(RedisCache.class);
        if (b) {
            //目标方法上存在RedisCaChe注解
            //直接方法Redis数据库,根据key获取对应值

            //获取类名
            String className = target.getClass().getName();
            String methodName = method.getName();
            //字节流
            StringBuilder stringBuilder = new StringBuilder();

            //拼接
            // com.baizhi.service.impl.BannerServiceImpl.selectAll(1,3...)
            stringBuilder.append(className).append(".").append(methodName).append("(");
            //参数
            for (int i = 0; i < args.length; i++) {
                stringBuilder.append(args[i]);
                //如果i是上面参数args数组最大下标 则后面不拼接“,”直接跳出
                if (i == args.length - 1) {
                    break;
                }
                stringBuilder.append(",");
            }
            stringBuilder.append(")");
            //获取key
            String key = stringBuilder.toString();
            //判断redis缓存是否存在这个key
            if (jedis.exists(key)) {
                String result = jedis.get(key);
                //返回json类型
                return JSONObject.toJSONString(result);
            } else {
                //返回结果没有key，把查到的key对应的值以json格式存入redis缓存
                Object result = point.proceed();
                jedis.set(key, JSONObject.toJSONString(result));
                return result;
            }
        } else {
            //目标方法不存在redisCache注解
            Object result = point.proceed();
            return result;
        }
    }


    //后置通知  此处针对项目的增删改
    @After("execution(* com.baizhi.service.serviceImpl.*.*(..)) && execution(* com.baizhi.service.serviceImpl.*.queryAll(..))")
    public void after(JoinPoint joinPoint) {
        Object target = joinPoint.getTarget();
        String className = target.getClass().getName();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        Method method = methodSignature.getMethod();
        boolean b = method.isAnnotationPresent(ClearRedisCache.class);
        if (b) {
            //如果有缓存则清除
            Set<String> keys = jedis.keys("*");
            for (String key : keys) {
                if (key.startsWith(className)) {
                    jedis.del(key);
                }
            }
        }
    }
}
