package com.damon;

import com.damon.service.HelloWorldService;
import com.damon.service.HelloWorldTwoService;
import com.damon.vo.Address;
import com.damon.vo.Person;
import com.damon.vo.User;
import org.apache.log4j.NDC;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class RemoteTest {
    
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testService() {
        logger.info("start...");
        ApplicationContext context = new ClassPathXmlApplicationContext("hessian-annotation.xml");
        HelloWorldService helloWorldService = context.getBean("helloWorldService", HelloWorldService.class);

        NDC.push("555sayHelloTwo--");
        logger.info("sayHelloTwo result={}", helloWorldService.sayHelloTwo("damon", "chow"));
        NDC.pop();
        NDC.push("555insertPerson--");
        logger.info("insertPerson result={}", helloWorldService.insertPerson(genPerson()));
        NDC.pop();
        NDC.push("666sayHello--");
        HelloWorldTwoService helloWorldTwoService = context.getBean("helloWorldTwoService", HelloWorldTwoService.class);
        logger.info("sayHello result={}", helloWorldTwoService.sayHello("damon"));
        NDC.pop();
        NDC.push("3433sayHello--");
        logger.info("sayHello result{}", helloWorldService.sayHello("damon"));
        logger.info("end.........");
        NDC.pop();
    }

    @Test
    public void testInsert() {
        logger.info("start...");
        ApplicationContext context = new ClassPathXmlApplicationContext("hessian-annotation.xml");
        HelloWorldTwoService helloWorldTwoService = context.getBean("helloWorldTwoService", HelloWorldTwoService.class);
        NDC.push("33344insertDB--");
        User user = new User();
        user.setId(88);
        user.setUserAddress("d");
        user.setUserName("老王");
        user.setUserAge(33);
        logger.info("insertDB result={}", helloWorldTwoService.insertDB(user));
        NDC.pop();
    }

    private Person genPerson() {
        Person person = new Person();
        person.setBirthday(new Date());
        person.setName("老王");
        person.setSex(1);
        person.setRich(true);
        Address address = new Address("中国","上海", 2);
        person.setAddress(address);
        return person;
    }

    @Test
    public void test() {
        logger.info("start...");
        ApplicationContext context = new ClassPathXmlApplicationContext("hessian-annotation.xml");
        HelloWorldService helloWorldService = context.getBean("helloWorldService", HelloWorldService.class);
        logger.info("result = "+helloWorldService.testOtherService("Da"));
    }

    @Test
    public void testTime() {
        logger.info("start...");
        ApplicationContext context = new ClassPathXmlApplicationContext("hessian-annotation.xml");
        HelloWorldService helloWorldService = context.getBean("helloWorldService", HelloWorldService.class);
        logger.info("result = "+helloWorldService.testTimestamp(new Timestamp(System.currentTimeMillis())));
    }

    @Test
    public void testPerson() {
        logger.info("start...");
        ApplicationContext context = new ClassPathXmlApplicationContext("hessian-annotation.xml");
        HelloWorldService helloWorldService = context.getBean("helloWorldService", HelloWorldService.class);
        Person person = new Person();
        person.setName("DDD");
//        person.setTime(new Timestamp(System.currentTimeMillis()));
//        person.setSqlDate(new java.sql.Date(System.currentTimeMillis()));
        person.setBirthday(new Date());
        person.setBigDecimal(new BigDecimal("10.01"));

        logger.info("result = "+helloWorldService.testPerson(person));
    }
}