package com.damon.dao;

import com.damon.vo.User;

import java.util.List;

/**
 * Created by Damon on 2017/5/15.
 */
public interface IUserOperation {

    User selectUserByID(int id);

    List<User> selectUsersByName(String userName);

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(int id);
}
