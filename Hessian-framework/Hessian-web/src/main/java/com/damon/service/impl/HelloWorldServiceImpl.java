package com.damon.service.impl;

import com.damon.service.HelloWorldService;
import org.springframework.stereotype.Service;

/**
 * Created by Damon on 2017/5/24.
 */
@Service
//@HessianService("helloWorldService")
public class HelloWorldServiceImpl implements HelloWorldService {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }

    @Override
    public String sayHello(int number) {
        return "Hello " + number;
    }
}
