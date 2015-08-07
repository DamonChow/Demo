package com.damon.test;

import java.lang.reflect.Array;

/**
 * 功能：
 *
 * Created by ZhouJW on 2015/1/15 15:51.
 */
public class ArrayTest {

    public static void main(String[] args) {
        Object o = Array.newInstance(String.class, 4);
        System.out.println(o.getClass());

        String[] a = new String[]{};
        System.out.println(a.getClass());
        System.out.println(String[].class);
        Integer i = 1;
        System.out.println(i.getClass());
        System.out.println(int.class);
    }
}
