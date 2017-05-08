package com.damon.example.thread.factory;

import java.util.concurrent.TimeUnit;

/**
 * 功能：
 *
 * Created by Domon Chow on 2015/5/7 15:50.
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