package com.damon.test.thread;


/**
 * 功能：
 *
 * Created by ZhouJiWei Date: 2015/8/7 Time: 10:45
 */
public class ThreadPrintABCOneByOne {


    private static void test1() throws InterruptedException {
        while(true){
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("A");
                }
            });
            thread1.start();
            thread1.join();

            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("B");
                }
            });
            thread2.start();
            thread2.join();

            Thread thread3 = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("C");
                }
            });
            thread3.start();
            thread3.join();
        }
    }
}
