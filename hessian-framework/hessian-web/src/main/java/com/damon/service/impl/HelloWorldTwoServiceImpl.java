package com.damon.service.impl;

import com.damon.dao.UserMapper;
import com.damon.hessian.common.HessianResponse;
import com.damon.service.HelloWorldTwoService;
import com.damon.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Damon on 2017/5/24.
 */
@Service
public class HelloWorldTwoServiceImpl implements HelloWorldTwoService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public HessianResponse<String> sayHello(String name) {
        return new HessianResponse<String>("Hello two  " + name);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor=RuntimeException.class)
    public HessianResponse<Long> insertDB(User user) {
        userMapper.addUser(user);
     throw new RuntimeException("收到错误");
//        return new HessianResponse<Long>(1L);
    }
}
