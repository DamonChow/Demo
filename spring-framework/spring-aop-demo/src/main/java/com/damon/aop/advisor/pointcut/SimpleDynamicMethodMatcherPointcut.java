package com.damon.aop.advisor.pointcut;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;

import java.lang.reflect.Method;

/**
 * Created by damon on 2017/7/13.
 */
@Slf4j
public class SimpleDynamicMethodMatcherPointcut extends DynamicMethodMatcherPointcut {

    @Override
    public ClassFilter getClassFilter() {
        return super.getClassFilter();
    }

    /**
     * 对方法进行静态切点检查  1.先做静态检查
     */
    @Override
    public boolean matches(Method method, Class clazz) {
        boolean result = "testAround".equals(method.getName());
        log.info("对|{}.{}|做静态检查|结果|{}", clazz.getName(), method.getName(), result);
        return result;
    }

    /**
     * 对方法进行动态切点检查  2.再做动态检查
     */
    public boolean matches(Method method, Class clazz, Object[] args) {
        boolean result = args[0] instanceof String;
        log.info("对|{}.{}|做动态检查|结果|{}", clazz.getName(), method.getName(), result);
        return result;
    }
}
