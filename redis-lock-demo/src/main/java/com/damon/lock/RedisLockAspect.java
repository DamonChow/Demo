package com.damon.lock;

import com.damon.client.RedisClient;
import com.damon.utli.AspectUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 功能：
 *
 * @author Damon
 * @since 2017/10/26 11:00
 */
@Aspect
@Component
public class RedisLockAspect {

    @Autowired
    private RedisClient redisClient;

    private Logger logger = LoggerFactory.getLogger(RedisLockAspect.class);

    @Around(value = " @annotation(com.damon.lock.RedisLock)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        //1.获取方法
        Object target = pjp.getTarget();
        Class<?> cls = AspectUtil.getTargetClass(target);
        Method specificMethod = AspectUtil.getSpecificMethod(pjp, cls);

        // 2.锁
        RedisLock lock = specificMethod.getAnnotation(RedisLock.class);
        String key = lock.key();
        Object[] args = pjp.getArgs();
        String lockKey = getLockKey(key, args);
        int expireSeconds = lock.expireSeconds();
        boolean retry = lock.retry();
        int retryTimes = lock.retryTimes();
        long delay = lock.delay();
        TimeUnit unit = lock.unit();
        logger.info("redisLock开始|key|{}|缓存时间|{}|重试机制|{}|重试次数|{}|间隔|{}|时间单位{}",
                lockKey, expireSeconds, retry, retryTimes, delay, unit);

        boolean locked = false;
        if (retry) {
            locked  = redisClient.tryAcquire(lockKey, expireSeconds, retryTimes, delay, unit);
        } else {
            locked  = redisClient.acquire(lockKey, expireSeconds);
        }

        if (!locked) {
            logger.info("redisLock结束，没有获取锁|key|{}", lockKey);
            throw new RuntimeException("没有获取到锁！");
        }

        try {
            logger.info("redisLock成功，获取锁|key|{}", lockKey);
            return pjp.proceed();
        } finally {
            redisClient.del(lockKey);
            logger.info("redisLock结束，释放锁|key|{}", lockKey);
        }
    }


    private String getLockKey(String key, Object[] args) {
        try {
            String[] param = (String[]) Optional.ofNullable(args).map(arg -> arg[0]).orElse(null);
            return MessageFormat.format(key, param);
        } catch (Exception e) {
            throw new IllegalArgumentException("切面失败，使用RedisLock注解，第一个参数为String数组");
        }

    }

}