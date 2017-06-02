package com.damon.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Damon on 2017/5/27.
 */
@Data
public class Address implements Serializable{

    private static final long serialVersionUID = -4935166493948170579L;

    private String country;

    private String city;

    private int top;

    public Address(String country, String city, int top) {
        this.country = country;
        this.city = city;
        this.top = top;
    }

}