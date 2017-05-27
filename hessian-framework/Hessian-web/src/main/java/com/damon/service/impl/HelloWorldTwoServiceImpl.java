package com.damon.service.impl;

import com.damon.service.HelloWorldTwoService;
import org.springframework.stereotype.Service;

/**
 * Created by Damon on 2017/5/24.
 */
@Service
public class HelloWorldTwoServiceImpl implements HelloWorldTwoService {

    @Override
    public String sayHello(String name) {
        return "Hello two  " + name;
    }
}
