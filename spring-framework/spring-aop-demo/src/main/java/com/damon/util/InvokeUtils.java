package com.damon.util;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * Created by damon on 2017/7/7.
 */
public class InvokeUtils {

    /**
     * Return a description for the given method invocation.
     *
     * @return the description
     */
    public static String getDescription(Method method, Object[] args, Object target) {
        return "方法 [" + method.getName() + "] , 类名 [" +
                target.getClass().getName() + "]" + "，参数 ["
                + StringUtils.arrayToCommaDelimitedString(args) + "]";
    }

    /**
     * Return a description for the given method invocation.
     *
     * @return the description
     */
    public static String getDescription(MethodInvocation invocation) {
        return "方法 [" + invocation.getMethod().getName() + "] , 类名 [" +
                invocation.getThis().getClass().getName() + "]" + "，参数 ["
                + StringUtils.arrayToCommaDelimitedString(invocation.getArguments()) + "]";
    }
}
