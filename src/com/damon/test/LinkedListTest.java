package com.damon.test;

import java.util.List;

/**
 * 功能：
 *
 * Created by ZhouJW on 2015/2/4 17:27.
 */
public class LinkedListTest {
    public static void main(String[] args) {
        List<String> list = new java.util.LinkedList<String>();
        list.add("11");
        list.add("22");
        list.add("223");

        Object[] objects = list.toArray();
        for(Object o: objects) {
            System.out.println(o);
        }
    }
}
