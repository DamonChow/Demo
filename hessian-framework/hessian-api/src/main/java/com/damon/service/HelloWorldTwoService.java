package com.damon.service;


import com.damon.hessian.common.HessianResponse;
import com.damon.vo.User;

/**
 * Created by Damon on 2017/5/24.
 */
public interface HelloWorldTwoService {

    HessianResponse<String> sayHello(String hello);

    HessianResponse<Long> insertDB(User user);
}
