package com.damon.hessian.support;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.SimpleTraceInterceptor;

/**
 * Created by Damon on 2017/5/26.
 */
public class HessianLogInterceptor implements MethodInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long start = System.currentTimeMillis();
        String invocationDescription = getInvocationDescription(invocation);
        logger.debug("接口调用开始：{}", invocationDescription);
        try {
            Object result = invocation.proceed();
            logger.debug("接口调用成功：{}, 用时: {} 毫秒，结果: {}.", invocationDescription, (System.currentTimeMillis()-start), result);
            return result;
        }
        catch (Throwable ex) {
            logger.error("接口调用失败：", ex);
            throw ex;
        }
    }

    /**
     * Return a description for the given method invocation.
     * @param invocation the invocation to describe
     * @return the description
     */
    protected String getInvocationDescription(MethodInvocation invocation) {
        return "method '" + invocation.getMethod().getName() + "' of class [" +
                invocation.getThis().getClass().getName() + "]";
    }
}
