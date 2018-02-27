package com.damon.lock.upgrade;

import com.damon.client.RedisClient;
import com.damon.utli.AspectUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 功能：
 *
 * @author Damon
 * @since 2017/10/26 11:00
 */
@Aspect
@Component
public class RedisLockUpgradeAspect {

    @Autowired
    private RedisClient redisClient;

    protected Logger logger = LoggerFactory.getLogger(RedisLockUpgradeAspect.class);

    @Around(value = " @annotation(com.damon.lock.upgrade.RedisLock)")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object target = pjp.getTarget();
        Class<?> cls = AspectUtil.getTargetClass(target);
        Method specificMethod = AspectUtil.getSpecificMethod(pjp, cls);

        // 2.锁
        RedisLock lock = specificMethod.getAnnotation(RedisLock.class);
        String key = lock.key();
        int expireSeconds = lock.expireSeconds();
        String lockKey = getLockKey(specificMethod, key, pjp.getArgs());
        boolean retry = lock.retry();
        int retryTimes = lock.retryTimes();
        long delay = lock.delay();
        TimeUnit unit = lock.unit();
        logger.info("redisLock开始|key|{}|缓存时间|{}|重试机制|{}|重试次数|{}|间隔|{}|时间单位{}",
                lockKey, expireSeconds, retry, retryTimes, delay, unit);

        boolean locked = false;
        if (retry) {
            locked = redisClient.tryAcquire(lockKey, expireSeconds, retryTimes, delay, unit);
        } else {
            locked = redisClient.acquire(lockKey, expireSeconds);
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

    public String getLockKey(Method method, String key, Object[] arguments) {
        LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] parameters = discoverer.getParameterNames(method);
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(key);
        EvaluationContext context = new StandardEvaluationContext();
        int length = parameters.length;
        if (length > 0) {
            IntStream.range(0, length).forEach(index -> context.setVariable(parameters[index], arguments[index]));
        }
        return expression.getValue(context, String.class);
    }

}