package com.damon.vo;

import java.io.Serializable;

/**
 * Created by Damon on 2017/5/27.
 */
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", top=" + top +
                '}';
    }
}