package com.damon.aop.advice.test;

import com.damon.service.SimpleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by damon on 2017/7/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/advice/advice.xml"})
public class SimpleApplicationTest {

    private static final String CONFIG_PATH = "advice/advice.xml";

    @Autowired
    private SimpleService simpleService;

    @Autowired
    private SimpleService aroundService;

    @Autowired
    private SimpleService beforeAndAfterService;

    @Autowired
    private SimpleService throwService;

    private ApplicationContext initApplicationContext() {
        return new ClassPathXmlApplicationContext(CONFIG_PATH);
    }

    @Test
    public void testSimple() {
//        simpleService = initApplicationContext().getBean("simpleService", SimpleService.class);
        simpleService.testBefore("Damon", 33);
    }

    @Test
    public void testBeforeAndAfter() {
        beforeAndAfterService.testBefore("[Before Damon]", 33);
        beforeAndAfterService.testAfter("[After Damon]",   33);
    }

    @Test
    public void testAround() {
        aroundService.testAround("[Around Damon]", 33);
    }

    @Test(expected = Exception.class)
    public void testThrows() {
        throwService.testThrows("[Throws Damon]", 33);
    }

}