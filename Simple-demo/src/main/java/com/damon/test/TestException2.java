package com.damon.test;

/**
 * 功能：
 *
 * Created by Domon Chow on 2015/1/23 10:09.
 */
public class TestException2 {

    int test1() {
        int i = 0;
        try {
            int t = 12/i;
        } catch (Exception e) {
            i=1;
            System.out.println("e===" + i);
            //System.exit(0);
            return i;
        } finally {
            i=2;
            System.out.println("r====="+i);
            return i;
        }
    }

    public static void main(String[] args) {
        int t = new TestException2().test1();
        System.out.println("result==="+t);
    }
}
