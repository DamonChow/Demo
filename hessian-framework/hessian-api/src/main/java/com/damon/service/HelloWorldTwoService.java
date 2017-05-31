package com.damon.service;


import com.damon.vo.User;

/**
 * Created by Damon on 2017/5/24.
 */
public interface HelloWorldTwoService {

    String sayHello(String hello);

    Long insertDB(User user);
}
