package com.damon.dao;

import com.damon.vo.Blog;
import org.mybatis.spring.annotation.MapperScan;

/**
 * Created by Damon on 2017/5/16.
 */
@MapperScan
public interface BlogMapper {

    Blog getBlogByID(int id);
}
