package com.damon.example;

/**
 * 功能：测试for循环性能
 *
 *<p>
 *    结论：
 *    (100*1000*10000)结果是：1000000000,共花时2ms
 *    (10000*1000*100)结果是：1000000000,共花时39ms
 *    (10000*100*1000)结果是：1000000000,共花时4ms
 *    (100*10000*1000)结果是：1000000000,共花时10ms
 *
 *    (100*100000)结果是：10000000,共花时7ms
 *    (100000*100)结果是：10000000,共花时3ms
 *
 *    3次循环次数少的放在最外层
 *
 *    循环次数少的放外层,本质也就是减少了实例化变量次数;
 *
 *</p>
 *
 * Created by Domon Chow on 2015/6/8 10:48.
 */
public class ForTest {

    public static void main(String[] args) {
        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
        test8();
        test7();
    }

    private static void test1() {
        long start = System.currentTimeMillis();
        int count = 0;
        for(int i=0;i<100;i++) {
            for (int j=0;j<1000;j++) {
                for (int t=0;t<10000;t++) {
                    count++;
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("(100*1000*10000)结果是：" + count + ",共花时" + (end - start) + "ms");
    }

    private static void test2() {
        long start = System.currentTimeMillis();
        int count = 0;
        for(int i=0;i<10000;i++) {
            for (int j=0;j<1000;j++) {
                for (int t=0;t<100;t++) {
                    count++;
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("(10000*1000*100)结果是：" + count + ",共花时" + (end-start) + "ms");
    }

    private static void test3() {
        long start = System.currentTimeMillis();
        int count = 0;
        for(int i=0;i<10000;i++) {
            for (int j=0;j<100;j++) {
                for (int t=0;t<1000;t++) {
                    count++;
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("(10000*100*1000)结果是：" + count + ",共花时" + (end-start) + "ms");
    }

    private static void test4() {
        long start = System.currentTimeMillis();
        int count = 0;
        for(int i=0;i<100;i++) {
            for (int j=0;j<10000;j++) {
                for (int t=0;t<1000;t++) {
                    count++;
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("(100*10000*1000)结果是：" + count + ",共花时" + (end-start) + "ms");
    }

    private static void test5() {
        long start = System.currentTimeMillis();
        int count = 0;
        for(int i=0;i<100;i++) {
            for (int j=0;j<100000;j++) {
                count++;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("(100*100000)结果是：" + count + ",共花时" + (end-start) + "ms");
    }

    private static void test6() {
        long start = System.currentTimeMillis();
        int count = 0;
        for(int i=0;i<100000;i++) {
            for (int j=0;j<100;j++) {
                count++;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("(100000*100)结果是：" + count + ",共花时" + (end-start) + "ms");
    }

    private static void test7() {
        long start = System.currentTimeMillis();
        int count = 0;
        for(int i=0;i<100000;i++) {
            for (int j=0;j<1000;j++) {
                count++;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("(100000*1000)结果是：" + count + ",共花时" + (end-start) + "ms");
    }

    private static void test8() {
        long start = System.currentTimeMillis();
        int count = 0;
        for(int i=0;i<1000;i++) {
            for (int j=0;j<100000;j++) {
                count++;
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("(1000*100000)结果是：" + count + ",共花时" + (end-start) + "ms");
    }
}
