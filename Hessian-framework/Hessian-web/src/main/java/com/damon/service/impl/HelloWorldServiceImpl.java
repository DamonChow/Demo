package com.damon.service.impl;

import com.damon.service.HelloWorldService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Damon on 2017/5/24.
 */
@Service
public class HelloWorldServiceImpl implements HelloWorldService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public String sayHello(String name) {
        logger.info("param name is = {}", name);
        String result = "Hello " + name;
        logger.info("result is = {}", result);
        return result;
    }

    @Override
    public String sayHello(int number) {
        logger.info("param number is = {}", number);
        String result = "Hello " + number;
        logger.info("result is = {}", result);
        return result;
    }
}
