package com.damon.test.thread.exception;

/**
 * 功能：线程异常
 *
 * Created by ZhouJW on 2015/5/7 14:49.
 */
public class Main {

    public static void main(String[] args) {
        Main m = new Main();
        m.test();
    }

    void test() {
        Task task=new Task();
        Thread thread=new Thread(task);
        thread.setUncaughtExceptionHandler(new ExceptionHandler());
        thread.start();
    }

    class Task implements Runnable {
        @Override
        public void run() {
            int numero = Integer.parseInt("TTT");
        }
    }

}
