package com.damon.service.impl;

import com.damon.service.HelloWorldService;
import com.damon.vo.Address;
import com.damon.vo.Person;
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
        logger.info("param is = {}", name);
        String result = "Hello " + name;
        logger.info("result is = {}", result);
        /*try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            logger.error("error : ", e);
        }*/
        return result;
    }

    @Override
    public String sayHelloTwo(String firstName, String secondName) {
        logger.info("param is = {}", firstName, secondName);
        String result = "Hello " + firstName + " " + secondName;
        logger.info("result is = {}", result);
//        throw new RuntimeException("错误.");
        return result;
    }

    @Override
    public Address insertPerson(Person person) {
        logger.info("param person is = {}", person);
        logger.info("result is = {}", person.getAddress());
        return person.getAddress();
    }
}
