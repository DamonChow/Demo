package com.damon.service.impl;

import com.damon.hessian.annotation.HessianService;
import com.damon.service.HelloWorldTwoService;

/**
 * Created by Damon on 2017/5/24.
 */
//@Service
@HessianService("helloWorldTwoService")
public class HelloWorldTwoServiceImpl implements HelloWorldTwoService {

    @Override
    public String sayHello(String name) {
        return "Hello two  " + name;
    }
}
