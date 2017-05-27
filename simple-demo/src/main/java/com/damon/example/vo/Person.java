package com.damon.example.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 功能：
 *
 * @author Damon
 * @since 2016/7/26 10:07
 */
public class Person implements Serializable{

    private static final long serialVersionUID = 6564395168203822697L;

    private int id;

    private String name;

    private int sex;

    private Date birthday;

    private List<Person> ChildList;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(int id, String name, int sex, Date birthday, List<Person> childList) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        ChildList = childList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<Person> getChildList() {
        return ChildList;
    }

    public void setChildList(List<Person> childList) {
        ChildList = childList;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", ChildList=" + ChildList +
                '}';
    }
}