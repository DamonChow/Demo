package com.damon.example.invoke;

import lombok.Data;

/**
 * Created by damon on 2017/6/16.
 */
@Data
public class PrivateObject {
    private String privateString = null;

    public PrivateObject(String privateString) {
        this.privateString = privateString;
    }
}
