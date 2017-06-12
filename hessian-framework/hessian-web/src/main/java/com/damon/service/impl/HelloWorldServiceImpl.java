package com.damon.service.impl;

import com.damon.hessian.common.HessianResponse;
import com.damon.manager.HelloWorldManager;
import com.damon.service.HelloWorldService;
import com.damon.vo.Address;
import com.damon.vo.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * Created by Damon on 2017/5/24.
 */
@Service
@Slf4j
public class HelloWorldServiceImpl implements HelloWorldService {

    @Autowired
    private HelloWorldManager helloWorldManager;

    @Override
    public HessianResponse<String> sayHello(String name) {
        log.info("param is = {}", name);
        String result = "Hello " + name;
        log.info("result is = {}", result);
        /*try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            log.error("error : ", e);
        }*/
        return new HessianResponse<String>(result);
    }

    @Override
    public HessianResponse<String> sayHelloTwo(String firstName, String secondName) {
        log.info("param is = {}", firstName, secondName);
        String result = "Hello " + firstName + " " + secondName;
        log.info("result is = {}", result);
//        throw new RuntimeException("错误.");
        return new HessianResponse<String>(result);
    }

    @Override
    public HessianResponse<Address> insertPerson(Person person) {
        log.info("param person is = {}", person);
        log.info("result is = {}", person.getAddress());
        return new HessianResponse<Address>(person.getAddress());
    }

    @Override
    public HessianResponse<Timestamp> testTimestamp(Timestamp timestamp) {
        return new HessianResponse<Timestamp>(timestamp);
    }

    @Override
    public HessianResponse<Person> testPerson(Person person) {
        log.info("响应：{}", person);
        return new HessianResponse<Person>(person);
    }
}
