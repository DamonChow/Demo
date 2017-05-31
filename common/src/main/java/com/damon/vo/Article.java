package com.damon.vo;

import java.io.Serializable;

/**
 * Created by Damon on 2017/5/16.
 */
public class Article implements Serializable {
    private static final long serialVersionUID = -7979642061353322366L;
    private int id;
    private User user;
    private String title;
    private String content;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
