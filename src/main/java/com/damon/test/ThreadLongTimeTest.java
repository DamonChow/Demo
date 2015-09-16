package com.damon.test;

/**
 * 功能：
 *
 * Created by Domon Chow on 2015/3/20 15:46.
 */
public class ThreadLongTimeTest {

    public static void main(String[] args) {
        ThreadLongTimeTest t = new ThreadLongTimeTest();
        t.test();
    }

    public void test (){
        String s = "ttt";
        ManyService m1 = new ManyService(s);
        ManyTwoService m2 = new ManyTwoService(s);
        ManyThreeService m3 = new ManyThreeService(s);

        m1.test();
        m2.test();
        m3.test();
        System.out.println("over");
    }

    class ManyService {
        private String s;
        public ManyService(String s) {
            this.s = s;
        }

        public void test() {
            System.out.println("jin ru---" + this.getClass());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //System.out.println("Thread1--" + Thread.currentThread().getName());
                        System.out.println("Thread1--" + s + "---" + Thread.currentThread().getName());
                        Thread.sleep(5000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    class ManyTwoService {
        private String s;
        public ManyTwoService(String s) {
            this.s = s;
        }

        public void test() {
            System.out.println("jin ru2--" + this.getClass());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("Thread2--" + s + "--" + Thread.currentThread().getName());
                        Thread.sleep(3000L);
                        //System.out.println("Thread2--" + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }
    class ManyThreeService {
        private String s;
        public ManyThreeService(String s) {
            this.s = s;
        }

        public void test() {
            System.out.println("jin ru3--" + this.getClass());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("Thread3---" + s +"--" +Thread.currentThread().getName());
                        Thread.sleep(3000L);
//                        /System.out.println("Thread3---" + Thread.currentThread().getName());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
