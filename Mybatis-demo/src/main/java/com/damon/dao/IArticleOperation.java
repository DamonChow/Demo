package com.damon.dao;

import com.damon.vo.Article;

import java.util.List;

/**
 * Created by Damon on 2017/5/16.
 */
public interface IArticleOperation {

    List<Article> getUserArticles(int userID);
}
