package com.damon.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Damon on 2017/5/15.
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 7712635580045496111L;

    private int id;

    private String userName;

    private int userAge;

    private String userAddress;

}