package com.damon;

import java.io.Serializable;

/**
 * 功能：
 *
 * @author zhoujiwei@idvert.com
 * @since 2018/1/4 11:03
 */
public class Apple implements Serializable {

    private Long id;

    private String name;

    public Apple(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
