package com.damon.test.thread.daemon;

import java.util.Date;

/**
 * 功能：
 *
 * Created by ZhouJW on 2015/5/7 14:45.
 */
public class Event {

    private Date date;
    private String event;

    public void setDate(Date date) {
        this.date = date;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Date getDate() {

        return date;
    }

    public String getEvent() {
        return event;
    }
}
