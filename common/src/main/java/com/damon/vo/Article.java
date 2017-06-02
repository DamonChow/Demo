package com.damon.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by Damon on 2017/5/16.
 */
@Data
public class Article implements Serializable {

    private static final long serialVersionUID = -7979642061353322366L;

    private int id;

    private User user;

    private String title;

    private String content;

}