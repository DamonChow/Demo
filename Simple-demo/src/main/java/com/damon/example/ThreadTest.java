package com.damon.example;

/**
 * 功能：
 *
 * Created by Domon Chow on 2015/3/31 10:46.
 */
public class ThreadTest {

    public static void main(String[] args) {
        int test = new ThreadTest().test();
        System.out.println(test);

    }

    public int test() {
        int t = 0;
        new Thread(new T(t)).start();

        return t;
    }

    private class T implements Runnable {
        private int t;

        public T(int t) {
            this.t = t;
        }

        public int getT() {

            return t;
        }

        public void setT(int t) {
            this.t = t;
        }

        public void run() {
            System.out.println("----------");
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t = 1;
        }
    }
}
