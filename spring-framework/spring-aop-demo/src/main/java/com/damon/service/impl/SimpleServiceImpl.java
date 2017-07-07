package com.damon.service.impl;

import com.damon.service.SimpleService;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by damon on 2017/7/7.
 */
@Slf4j
public class SimpleServiceImpl implements SimpleService {

    @Override
    public String testBefore(String name, int age) {
        String result = "testBefore|hello" + name + ", you are " + age + " old.";
        log.info("结果|{}", result);
        return result;
    }

    @Override
    public String testAfter(String name, int age) {
        String result = "testAfter|hello" + name + ", you are " + age + " old.";
        log.info("结果|{}", result);
        return result;
    }

    @Override
    public String testAround(String name, int age) {
        String result = "testAround|hello" + name + ", you are " + age + " old.";
        log.info("结果|{}", result);
        return result;
    }

    @Override
    public String testThrows(String name, int age) {
        String message = "testThrows|hello" + name + ", you are " + age + " old.";
        log.error("结果|{}", message);
        throw new RuntimeException(message);
    }

}