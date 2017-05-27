package com.damon.example;

/**
 * Created by ASUS on 2015/1/9.
 */
public class ThreadLocalTest {

    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 10;
        }
    };

    private static int unSafeInt;


    static class MyThread implements Runnable {

        @Override
        public void run() {
            System.out.println("Thread " + Thread.currentThread().getName()+"----------------");
            for (int i = 0;i<3;i++) {
                Integer integer = threadLocal.get();
                threadLocal.set(i);
                System.out.println("int safe =" + integer);
                System.out.println("int unsafe =" + unSafeInt);
                unSafeInt = i;
            }
        }
    }
    public static void main(String[] args) {
        new Thread(new MyThread()).start();
        new Thread(new MyThread()).start();
        new Thread(new MyThread()).start();
    }
}
