package com.damon.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 功能：
 *
 * @author Damon
 * @since 2016/7/26 10:07
 */
@Data
@NoArgsConstructor
public class Person implements Serializable{

    private static final long serialVersionUID = 6564395168203822697L;

    private Long id;

    private boolean rich;

    private String name;

    private int sex;

    private int age;

    private String ageDesc;

    private Date birthday;

    private java.sql.Date sqlDate;

    private Timestamp time;

    private BigDecimal bigDecimal;

    boolean isSelected;

    private List<Person> ChildList;

    private Address address;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person(Long id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Person(Long id, String name, int age, String ageDesc) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.ageDesc = ageDesc;
    }

    public Person(boolean rich, String name, int sex, Date birthday, List<Person> childList, Address address) {
        this.rich = rich;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        ChildList = childList;
        this.address = address;
    }

}