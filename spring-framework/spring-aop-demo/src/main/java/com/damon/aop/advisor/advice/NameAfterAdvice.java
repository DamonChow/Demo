package com.damon.aop.advisor.advice;

import com.damon.util.InvokeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * Created by damon on 2017/7/7.
 */
@Slf4j
public class NameAfterAdvice implements AfterReturningAdvice {

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        log.info("调用完成|调用详情={}|结果={}", InvokeUtils.getDescription(method, args, target), returnValue);
    }

}