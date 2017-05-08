package com.damon.fastjson.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能：
 *
 * @author Damon
 * @since 2015/12/1 10:18
 */
public class User implements Serializable {

    private String name;

    private Date birthday;

    private int age;

    private char sex;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }
}
