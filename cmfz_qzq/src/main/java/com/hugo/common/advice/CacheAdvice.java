package com.hugo.common.advice;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.Jedis;

//注解  自动配置
@Configuration
@Aspect
public class CacheAdvice {
    @Autowired
    private Jedis jedis;

    /**
     * 环绕方法  用来纳入缓存
     *
     * @return 注解的切入点表达式：@注解全限定名
     * 指切入点是带有注解的方法
     */
    @Around("@annotation(com.hugo.common.annotations.Cache)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        //声明一个变量yigongqu以供全局使用
        Object proceed = null;

        //先去redis中获取key，判断是否存在，以类的全限定名为key
        //获取类名
        String hashkey = proceedingJoinPoint.getTarget().getClass().getName();

        //获取目标方法的返回值类型
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        //获取hash中map的键key
        String mapkey = getMapkey(proceedingJoinPoint);

        //判断是否存在
        if (jedis.hexists(hashkey, mapkey)) {
            //存在,  从redis中获取json格式的数据
            String json = jedis.hget(hashkey, mapkey);
            //json字符串转为对应类型 对象
            proceed = JSONObject.parseObject(json, signature.getMethod().getGenericReturnType());

        } else {
            //不存在  放行 返回目标方法的返回值
            proceed = proceedingJoinPoint.proceed();
            //执行完目标方法后，把目标返回值存入redis中, 先把对象转换为json字符串
            String json = JSONObject.toJSONStringWithDateFormat(proceed, "yyyy-MM-dd");
            //存入redis中
            jedis.hset(hashkey, mapkey, json);
            //为了日期格式显示一致。先把日期格式为util类型的存进去，转为yyyy-MM-dd然后再取出来就是标准格式
            proceed = JSONObject.parseObject(json, signature.getMethod().getGenericReturnType());
        }
        return proceed;
    }

    /**
     * 获取hash中map的key
     *
     * @param proceedingJoinPoint
     * @return
     */
    public String getMapkey(ProceedingJoinPoint proceedingJoinPoint) {
        //获取方法名
        String mapkey = proceedingJoinPoint.getSignature().toString();
        //获取参数
        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            //遍历参数，把参数和方法名拼接在一起作为hash中map的key
            mapkey += arg;
        }
        //返回md5化的mapkey
        return DigestUtils.md5DigestAsHex(mapkey.getBytes());
    }

    /**
     * 清除缓存
     * 一定目标方法执行完成之后之后清除缓存
     */
    @After("@annotation(com.hugo.common.annotations.FlushCache)")
    public void after(JoinPoint joinPoint) {
        //清楚缓存，业务中整个hash
        String hashkey = joinPoint.getTarget().getClass().getName();
        jedis.del(hashkey);
    }
}
