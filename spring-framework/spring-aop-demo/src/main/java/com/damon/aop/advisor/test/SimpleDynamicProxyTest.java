package com.damon.aop.advisor.test;

import com.damon.service.SimpleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by damon on 2017/7/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/advisor/advisor.xml"})
public class SimpleDynamicProxyTest {

    @Resource(name="dynamicProxy")
    private SimpleService dynamicProxy;

    @Test
    public void testBefore() {
        dynamicProxy.testBefore("[Before Damon]", 33);
    }

    @Test
    public void testAfter() {
        dynamicProxy.testAfter("[After Damon]",   33);
    }

    @Test
    public void testAround() {
        dynamicProxy.testAround("[Around Damon]", 33);
    }

    @Test
    public void testAround2() {
        dynamicProxy.testAround(33);
    }

    @Test(expected = Exception.class)
    public void testThrows() {
        dynamicProxy.testThrows("[Throws Damon]", 33);
    }


}