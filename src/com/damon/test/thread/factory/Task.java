package com.damon.test.thread.factory;

import java.util.concurrent.TimeUnit;

/**
 * 功能：
 *
 * Created by ZhouJW on 2015/5/7 15:50.
 */
public class Task implements Runnable {
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}