package com.damon.fastjson.vo;

import java.io.Serializable;

/**
 * 功能：
 *
 * @author Damon
 * @since 2015/12/1 10:28
 */
public class People implements Serializable{

    private static final long serialVersionUID = -6834930787791466950L;

    private User user;

    private Address address;

    @Override
    public String toString() {
        return "People{" +
                "user=" + user +
                ", address=" + address +
                '}';
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
