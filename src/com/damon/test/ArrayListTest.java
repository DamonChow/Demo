package com.damon.test;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能：ArrayList  为空，或者添加一个null
 *
 * Created by ZhouJW on 2015/1/15 9:37.
 */
public class ArrayListTest {

    public static void main(String[] args) {
        List<Object> list = new ArrayList<Object>();
        list.add(1);
        Object s = list.get(0);
        if(s instanceof String) {
        System.out.println("String");
        } else if(s instanceof Integer) {
        System.out.println("Integer");
        } else {
        System.out.println("nothing");
        }
        System.out.println(s);

        try(FileWriter file = new FileWriter(".\\data\\log.txt");
            PrintWriter pw = new PrintWriter(file);) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
