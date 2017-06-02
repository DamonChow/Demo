package com.damon.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Damon on 2017/5/16.
 */
@Data
public class Blog implements Serializable {

    private static final long serialVersionUID = 2938225038130815831L;

    private int id;

    private String title;

    private List<Article> articles;

}