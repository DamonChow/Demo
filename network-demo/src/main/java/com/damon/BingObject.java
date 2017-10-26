package com.damon;

import java.io.Serializable;
import java.util.List;

/**
 * 功能：
 *
 * @author zhoujiwei@idvert.com
 * @since 2017/10/23 15:36
 */
public class BingObject implements Serializable {

    private static final long serialVersionUID = 2310998408333791901L;

    private String name;

    List<Object> test;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Object> getTest() {
        return test;
    }

    public void setTest(List<Object> test) {
        this.test = test;
    }

    public BingObject(String name) {
        this.name = name;
    }

    public BingObject(List<Object> test) {

        this.test = test;
    }

    public BingObject(String name, List<Object> test) {

        this.name = name;
        this.test = test;
    }
}
