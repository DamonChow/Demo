package com.damon.dao;

import com.damon.vo.Blog;

/**
 * Created by Damon on 2017/5/16.
 */
public interface IBlogOperation {

    Blog getBlogByID(int id);
}
