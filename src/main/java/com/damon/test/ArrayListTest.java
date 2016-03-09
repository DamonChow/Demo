package com.damon.test;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能：ArrayList  为空，或者添加一个null
 *
 * Created by Domon Chow on 2015/1/15 9:37.
 */
public class ArrayListTest {

    public static void main(String[] args) {
        /*List<Object> list = new ArrayList<Object>();
        list.add(1);
        Object s = list.get(0);
        if(s instanceof String) {
        System.out.println("String");
        } else if(s instanceof Integer) {
        System.out.println("Integer");
        } else {
        System.out.println("nothing");
        }
        System.out.println(s);*/

        /*try(FileWriter file = new FileWriter(".\\data\\log.txt");
            PrintWriter pw = new PrintWriter(file);) {

        } catch (IOException e) {
            e.printStackTrace();
        }*/

        System.out.println("----------------------------ge--------------");


        List<String> list3 = new ArrayList<String>();
        for (String s3 : list3) {
            System.out.println(s3);
        }
        System.out.println("----------------------------ge2--------------");
        list3.add("23");
        list3.add("23d");
        list3.add("23df");
        System.out.println(new StringBuilder().append(list3));
        /*List<String> list2 = null;
        for (String s2 : list2) {
            System.out.println(s2);
        }*/
    }
}
