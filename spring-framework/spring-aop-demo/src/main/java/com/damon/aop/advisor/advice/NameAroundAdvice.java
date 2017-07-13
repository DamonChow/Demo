package com.damon.aop.advisor.advice;

import com.damon.util.InvokeUtils;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;


/**
 * Created by damon on 2017/7/7.
 */
@Slf4j
public class NameAroundAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("开始调用|调用详情={}", InvokeUtils.getDescription(invocation));

        //通过反射调用目标方法
        Object returnValue = invocation.proceed();

        log.info("调用完成|调用详情={}|结果={}", InvokeUtils.getDescription(invocation), returnValue);
        return returnValue;
    }
}
