package com.damon.service;


import com.damon.vo.Address;
import com.damon.vo.Person;

/**
 * Created by Damon on 2017/5/24.
 */
public interface HelloWorldService {

    String sayHello(String hello);

    String sayHelloTwo(String firstName, String secondName);

    Address insertPerson(Person person);
}
