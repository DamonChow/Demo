package com.damon.aop.advice;

import com.damon.util.InvokeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * Created by damon on 2017/7/7.
 */
@Slf4j
public class SimpleThrowAdvice implements ThrowsAdvice {

    public void afterThrowing(Method method, Object[] args, Object target, Exception e) {
        log.error("调用异常|调用详情={}|异常", InvokeUtils.getDescription(method, args, target), e);
    }
}
