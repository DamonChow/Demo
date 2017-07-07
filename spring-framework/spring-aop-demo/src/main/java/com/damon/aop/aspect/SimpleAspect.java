package com.damon.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by Damon on 2017/7/7.
 */

@Aspect
@Slf4j
public class SimpleAspect {

    @Pointcut("execution(* com.damon.service.impl.*.*(..))")
    public void pointcut() {

    }

    @Before(value = " pointcut()")
    public void before() {
        log.info("before|方法执行前执行.....");
    }

    @Around(value = " pointcut()")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("around|方法环绕start.....");
        pjp.proceed();
        log.info("around|方法环绕end.....");
    }

    @After(value = " pointcut()")
    public void after() {
        log.info("after|方法最后执行.....");
    }

    @AfterReturning(value = " pointcut()")
    public void afterReturning() {
        log.info("AfterReturning|方法执行完执行.....");
    }

    @AfterThrowing(value = " pointcut()")
    public void throwing() {
        log.info("throwing|方法异常时执行.....");
    }

}