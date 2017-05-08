package com.damon.fastjson;

import com.alibaba.fastjson.JSON;
import com.damon.fastjson.vo.Address;
import com.damon.fastjson.vo.People;
import com.damon.fastjson.vo.User;
import org.junit.Test;

import java.util.Date;

/**
 * 功能：
 *
 * @author Damon
 * @since 2015/12/1 10:18
 */
public class FastJsonTest {

    @Test
    public void test() {
        User user = new User();
        user.setAge(23);
        user.setSex('m');
        user.setBirthday(new Date());
        user.setName("李磊");

        Address address = new Address();
        address.setCity("sz");
        address.setArea("罗湖");
        address.setStreet("黄贝街道");
        address.setNumber("10004号");

        People p = new People();
        p.setAddress(address);
        p.setUser(user);

        System.out.println(JSON.toJSONString(p));
    }
}
