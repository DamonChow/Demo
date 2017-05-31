package com.damon.dao;

import com.damon.vo.Article;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * Created by Damon on 2017/5/16.
 */
@MapperScan
public interface ArticleMapper {

    List<Article> getUserArticles(int userID);
}
