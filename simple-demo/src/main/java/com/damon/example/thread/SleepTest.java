package com.damon.example.thread;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 功能：
 *
 * Created by damon on 2015/5/7 12:03.
 */
public class SleepTest {

    public static void main(String[] args) {
        SleepTest t = new SleepTest();
        t.test();
    }

    void test() {
        FileClock clock= new FileClock();
        Thread thread=new Thread(clock);
        thread.start();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();
    }

    class FileClock implements Runnable {

        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.printf("%s\n", new Date());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    System.out.printf("The FileClock has been interrupted");
                }
            }
        }
    }
}
