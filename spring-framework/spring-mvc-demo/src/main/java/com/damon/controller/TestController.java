package com.damon.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能：demo
 *
 * @author Damon
 * @since 2017/11/6 14:27
 */
@RestController
@RequestMapping(value="/v1/test")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value = "/greet", method = RequestMethod.GET)
    public String greet(@RequestParam("name") String name){
        String greet = "hello " + name;
        logger.info("greet= {}", greet);
        return greet;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){
        System.out.println("-----");
        return "test";
    }

}
