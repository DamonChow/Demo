package com.damon.service.impl;

import com.damon.hessian.annotation.HessianService;
import com.damon.service.HelloWorldService;

/**
 * Created by Damon on 2017/5/24.
 */
//@Service
@HessianService("helloWorldService")
public class HelloWorldServiceImpl implements HelloWorldService {

    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
