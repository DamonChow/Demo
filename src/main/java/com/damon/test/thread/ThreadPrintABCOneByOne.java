package com.damon.test.thread;


import org.junit.Test;

/**
 * 功能：
 *
 * Created by Domon Chow Date: 2015/8/7 Time: 10:45
 */
public class ThreadPrintABCOneByOne {

    @Test
    public void test1() throws InterruptedException {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("A");
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("B");
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("C");
            }
        });

        while(true){
            thread1.start();
            thread1.join();
            thread2.start();
            thread2.join();
            thread3.start();
            thread3.join();
        }
    }

    public void test2() {

    }
}
