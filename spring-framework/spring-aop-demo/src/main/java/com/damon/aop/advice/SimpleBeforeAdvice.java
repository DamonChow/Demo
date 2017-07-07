package com.damon.aop.advice;

import com.damon.util.InvokeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * Created by damon on 2017/7/7.
 */
@Slf4j
public class SimpleBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        log.info("开始调用|调用详情={}", InvokeUtils.getDescription(method, args, target));
    }

}