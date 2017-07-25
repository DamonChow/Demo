package com.damon.aop.aspect.test;

import com.damon.service.SimpleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by damon on 2017/7/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/aspect/aspect-simple.xml"})
@Slf4j
public class SimpleAspectTest {

    @Autowired
    private SimpleService simpleService;

    @Test
    public void testSimple() {
        simpleService.testBefore("Damon", 33);
    }

    @Test(expected = Exception.class)
    public void testThrows() {
        try {
            simpleService.testThrows("[Throws Damon]", 33);
        } catch (Exception e) {
            log.error("错误{}|", "aa", e);
        }
    }

}