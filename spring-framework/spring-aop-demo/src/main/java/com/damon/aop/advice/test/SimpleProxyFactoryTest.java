package com.damon.aop.advice.test;

import com.damon.aop.advice.SimpleAfterAdvice;
import com.damon.aop.advice.SimpleAroundAdvice;
import com.damon.aop.advice.SimpleBeforeAdvice;
import com.damon.aop.advice.SimpleThrowAdvice;
import com.damon.service.SimpleService;
import com.damon.service.impl.SimpleServiceImpl;
import org.aopalliance.aop.Advice;
import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;

/**
 * Created by damon on 2017/7/7.
 */
public class SimpleProxyFactoryTest {

    private SimpleService getProxy(Advice advice) {
        ProxyFactory factory = new ProxyFactory();
        factory.addAdvice(advice);
        factory.setTarget(new SimpleServiceImpl());
        return (SimpleService) factory.getProxy();
    }

    @Test
    public void testBefore() {
        Advice advice = new SimpleBeforeAdvice();
        SimpleService proxy = getProxy(advice);
        proxy.testBefore("Damon", 16);
    }

    @Test
    public void testAfter() {
        Advice advice = new SimpleAfterAdvice();
        SimpleService proxy = getProxy(advice);
        proxy.testAfter("Damon", 16);
    }

    @Test
    public void testAround() {
        Advice advice = new SimpleAroundAdvice();
        SimpleService proxy = getProxy(advice);
        proxy.testAround("Damon", 16);
    }

    @Test(expected = Exception.class)
    public void testThrows() {
        Advice advice = new SimpleThrowAdvice();
        SimpleService proxy = getProxy(advice);
        proxy.testThrows("Damon", 16);
    }

}