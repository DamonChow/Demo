package com.damon.utli;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * 功能：
 *
 * @author zhoujiwei@idvert.com
 * @since 2018/1/4 14:32
 */
public class AspectUtil {

    /**
     * 获取目标Class
     *
     * @param target
     * @return
     */
    public static Class<?> getTargetClass(Object target) {
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(target);
        if (targetClass == null) {
            targetClass = target.getClass();
        }
        return targetClass;
    }

    /**
     * 获取指定方法
     *
     * @param pjp
     * @param targetClass
     * @return
     */
    public static Method getSpecificMethod(ProceedingJoinPoint pjp, Class<?> targetClass) {
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
        specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
        return specificMethod;
    }

}
