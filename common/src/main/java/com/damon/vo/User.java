package com.damon.vo;

import java.io.Serializable;

/**
 *
 * Created by Damon on 2017/5/15.
 */
public class User implements Serializable{
    private static final long serialVersionUID = 7712635580045496111L;
    private int id;
    private String userName;
    private int userAge;
    private String userAddress;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    @Override
    public String toString(){
        return this.userName+" "+this.userAge+" "+this.userAddress;
    }
}
