package com.damon.vo;

import java.util.List;

/**
 * Created by Damon on 2017/5/16.
 */
public class Blog {
    private int id;
    private String title;
    private List<Article> articles;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public List<Article> getArticles() {
        return articles;
    }
    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
