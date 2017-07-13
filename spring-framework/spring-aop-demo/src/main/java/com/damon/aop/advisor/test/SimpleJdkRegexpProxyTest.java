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
public class SimpleJdkRegexpProxyTest {

    @Resource(name="jdkRegexpProxy")
    private SimpleService jdkRegexpProxy;

    @Test
    public void testBefore() {
        jdkRegexpProxy.testBefore("[Before Damon]", 33);
    }

    @Test
    public void testAfter() {
        jdkRegexpProxy.testAfter("[After Damon]",   33);
    }

    @Test
    public void testAround() {
        jdkRegexpProxy.testAround("[Around Damon]", 33);
    }

    @Test(expected = Exception.class)
    public void testThrows() {
        jdkRegexpProxy.testThrows("[Throws Damon]", 33);
    }


}