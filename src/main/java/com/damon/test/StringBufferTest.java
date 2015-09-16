package com.damon.test;

/**
 * 功能：
 *
 * Created by Domon Chow on 2015/3/2 17:34.
 */
public class StringBufferTest {

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();

        for (int i = 0;i<4;i++) {
            sb.append("'ttt" + i + "',");
        }

        if (sb.length() != 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        System.out.println(sb.toString());
    }
}
