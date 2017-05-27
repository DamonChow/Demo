package com.damon;

import com.damon.service.HelloWorldService;
import com.damon.service.HelloWorldTwoService;
import org.apache.log4j.NDC;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RemoteTest {
    
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testService() {
        logger.info("start...");
        ApplicationContext context = new ClassPathXmlApplicationContext("hessian-annotation.xml");
        HelloWorldService helloWorldService = context.getBean("helloWorldService", HelloWorldService.class);

        NDC.push("555--");
        logger.info(helloWorldService.sayHelloTwo("damon", "chow"));
        NDC.pop();
        NDC.push("666--");
        HelloWorldTwoService helloWorldTwoService = context.getBean("helloWorldTwoService", HelloWorldTwoService.class);
        logger.info(helloWorldTwoService.sayHello("damon"));
        NDC.pop();
        NDC.push("333--");
        logger.info(helloWorldService.sayHello("damon"));
        logger.info("end.........");
        NDC.pop();
    }

}