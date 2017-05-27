package com.damon.vo;

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

    private Long id;

    private boolean rich;

    private String name;

    private int sex;

    private int age;

    private Date birthday;

    boolean isSelected;

    private List<Person> ChildList;

    private Address address;

    public boolean isRich() {
        return rich;
    }

    public void setRich(boolean rich) {
        this.rich = rich;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
    }

    public Person(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Person(boolean rich, String name, int sex, Date birthday, List<Person> childList, Address address) {
        this.rich = rich;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        ChildList = childList;
        this.address = address;
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

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public List<Person> getChildList() {
        return ChildList;
    }

    public void setChildList(List<Person> childList) {
        ChildList = childList;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", rich=" + rich +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                ", birthday=" + birthday +
                ", isSelected=" + isSelected +
                ", ChildList=" + ChildList +
                ", address=" + address +
                '}';
    }
}