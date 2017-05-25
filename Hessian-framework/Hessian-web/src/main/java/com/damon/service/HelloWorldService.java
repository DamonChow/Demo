package com.damon.service;

import com.damon.hessian.common.ModuleEnum;
import com.damon.hessian.annotation.HessianInterface;

/**
 * Created by Damon on 2017/5/24.
 */
@HessianInterface(module = ModuleEnum.DEMO_APP, remote = "/remote")
public interface HelloWorldService {

    String sayHello(String hello);
}
