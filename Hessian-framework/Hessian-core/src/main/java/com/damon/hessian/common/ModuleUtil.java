package com.damon.hessian.common;

import org.springframework.beans.factory.annotation.Value;

/**
 * Created by zhoujiwei on 2017/5/25.
 */
public class ModuleUtil {

    @Value("${demo.app.url}")
    private String demo;

    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        System.out.println("test====" + test);
        this.test = test;
    }

    public String getDemo() {
        return demo;
    }

    public void setDemo(String demo) {
        System.out.println("demo====" + demo);
        this.demo = demo;
    }
}
