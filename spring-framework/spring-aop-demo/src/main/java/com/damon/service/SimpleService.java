package com.damon.service;

/**
 * Created by damon on 2017/7/7.
 */
public interface SimpleService {

    String testBefore(String name, int age);

    String testAfter(String name, int age);

    String testAround(String name, int age);

    String testThrows(String name, int age);
}
