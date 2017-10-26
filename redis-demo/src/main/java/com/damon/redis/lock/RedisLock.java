package com.damon.redis.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * 功能：redis锁
 *
 * @author zhoujiwei@idvert.com
 * @since 2017/10/26 10:47
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisLock {

    /**锁名 例：AA:BB{0}:{1}*/
    String key() default "";

    /**锁过期时间 默认60秒*/
    int expireSeconds() default 60;

    /**释放需要重试机制 默认不重试*/
    boolean retry() default false;

    /**重试次数 默认5次*/
    int retryTimes() default 10;

    /**重试间隔 默认1*/
    long delay() default 1L;

    /**重试间隔单位 默认秒*/
    TimeUnit unit() default TimeUnit.SECONDS;
}