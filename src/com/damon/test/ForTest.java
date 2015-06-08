package com.damon.test;

/**
 * 功能：测试for循环性能
 *
 *<p>
 *    结论：
 *    结果是：10000000,共花时1ms
 *    结果是：10000000,共花时5ms
 *
 *    循环次数少的放在最外层
 *
 *</p>
 *
 * Created by ZhouJW on 2015/6/8 10:48.
 */
public class ForTest {

    public static void main(String[] args) {
        test1();
        test2();
    }

    private static void test1() {
        long start = System.currentTimeMillis();
        int count = 0;
        for(int i=0;i<10;i++) {
            for (int j=0;j<1000;j++) {
                for (int t=0;t<1000;t++) {
                    count++;
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("结果是：" + count + ",共花时" + (end-start) + "ms");
    }

    private static void test2() {
        long start = System.currentTimeMillis();
        int count = 0;
        for(int i=0;i<1000;i++) {
            for (int j=0;j<1000;j++) {
                for (int t=0;t<10;t++) {
                    count++;
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("结果是：" + count + ",共花时" + (end-start) + "ms");
    }
}
