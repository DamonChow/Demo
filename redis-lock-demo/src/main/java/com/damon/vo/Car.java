package com.damon.vo;

import java.io.Serializable;

/**
 * 功能：
 *
 * @author zhoujiwei@idvert.com
 * @since 2018/1/4 11:22
 */
public class Car implements Serializable {

    private static final long serialVersionUID = -8301494041960500405L;

    private Integer id;

    private String name;

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
