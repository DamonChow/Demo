package com.damon.service;

import com.damon.lock.upgrade.RedisLock;
import com.damon.vo.Car;
import com.damon.vo.Phone;
import org.springframework.stereotype.Service;

/**
 * 功能：
 *
 * @author zhoujiwei@idvert.com
 * @since 2018/1/4 11:22
 */
@Service
public class RedisService {

    @RedisLock(key = "'lock:name:'+#name+':carId:'+#car.id")
    public String callCar(String name, Car car) {
        return name + "call a car which is [" + car + "]";
    }


    @RedisLock(key = "'lock:name:'+#name+':carId:'+#phone.id")
    public String buyPhone(String name, Phone phone) {
        return name + "buy a phone which is [" + phone + "]";
    }


}
