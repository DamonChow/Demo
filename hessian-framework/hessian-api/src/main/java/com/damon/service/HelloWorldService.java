package com.damon.service;


import com.damon.hessian.common.HessianResponse;
import com.damon.vo.Address;
import com.damon.vo.Person;

/**
 * Created by Damon on 2017/5/24.
 */
public interface HelloWorldService {

    HessianResponse<String> sayHello(String hello);

    HessianResponse<String> sayHelloTwo(String firstName, String secondName);

    HessianResponse<Address> insertPerson(Person person);
}
