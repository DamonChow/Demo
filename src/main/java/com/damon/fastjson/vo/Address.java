package com.damon.fastjson.vo;

import java.io.Serializable;

/**
 * 功能：
 *
 * @author Damon
 * @since 2015/12/1 10:19
 */
public class Address implements Serializable {

    private static final long serialVersionUID = 6206479755074534374L;

    private String city;

    private String area;

    private String street;

    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
