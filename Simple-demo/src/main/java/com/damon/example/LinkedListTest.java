package com.damon.example;

import org.junit.Test;

import java.util.*;

/**
 * 功能：
 *
 * Created by Domon Chow on 2015/2/4 17:27.
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

    @Test
    public void testOrder() {
        //String[] source = {"601","603","602","@22","@11#1","@11"};
        String[] source = {"601","603","602","22","111","11"};
        List<Long> needSortList = new ArrayList<Long>();
        List<String> remainList = new ArrayList<String>();
        List<String> result = new ArrayList<String>();
        for (int i=0;i<source.length;i++) {
            try {
                if (source[i].contains("@")) {
                    remainList.add(source[i]);
                } else {
                    long id = Long.parseLong(source[i]);
                    needSortList.add(id);
                }
            } catch (NumberFormatException e) {
                System.out.println("error :::" + e.getMessage());
                return;
            }
        }

        Collections.sort(needSortList);
        for (Long id : needSortList) {
            result.add(id.toString());
        }
        result.addAll(remainList);
        System.out.println("result ::" + result);


       /*List<String> beforeList = new ArrayList<String>(Arrays.asList(source));
        for (String item : beforeList) {
            if (item.contains("@")) {
                index = beforeList.indexOf(item);
                break;
            }
        }

        System.out.println("before :::" + beforeList);
        List<String> needSortList = null;
        List<String> remainList = null;

        if (index != 0) {
            needSortList = beforeList.subList(0, index);
            remainList = beforeList.subList(index, beforeList.size());
            System.out.println("needSortList::" + needSortList);
            System.out.println("remainList::" + remainList);
        } else {
            needSortList = beforeList;
        }

        Collections.sort(needSortList);
        if (remainList != null) {
            needSortList.addAll(remainList);
        }
        System.out.println("sort list ::" + needSortList);*/
    }
}
