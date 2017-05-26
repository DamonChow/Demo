package com.damon;

import com.damon.hessian.common.ModuleUtil;
import com.damon.service.HelloWorldService;
import com.damon.service.HelloWorldTwoService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RemoteTest {

    @Test
    public void testService() {
        System.out.println("start...");
        ApplicationContext context = new ClassPathXmlApplicationContext("hessian-annotation.xml");
        HelloWorldService helloWorldService = context.getBean("helloWorldService", HelloWorldService.class);
        System.out.println(helloWorldService.sayHello("damon"));

        HelloWorldTwoService helloWorldTwoService = context.getBean("helloWorldTwoService", HelloWorldTwoService.class);
        System.out.println(helloWorldTwoService.sayHello("damon"));
        System.out.println("end.........");
    }

}