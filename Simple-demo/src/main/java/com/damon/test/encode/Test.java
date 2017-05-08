package com.damon.test.encode;

import java.io.UnsupportedEncodingException;

/**
 * 功能：
 *
 * @author Damon
 * @since 2015/10/10 15:45
 */
public class Test {

    public static void main(String[] args) {
        String s = " mq 鎺ㄩ�� end";

        System.out.println(s);

        String ss = "mq 推送 start";

        System.out.println("-----------");
        try {
            byte[] defaultBytes = ss.getBytes();
            System.out.println("utf-8======="+new String(defaultBytes,"utf-8"));
            System.out.println("GBK========="+new String(defaultBytes,"GBK"));
            System.out.println("GB2312======"+new String(defaultBytes,"GB2312"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
