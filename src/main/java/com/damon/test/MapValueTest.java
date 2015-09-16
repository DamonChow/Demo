package com.damon.test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 功能：
 *
 * Created by Domon Chow on 2015/6/4 18:22.
 */
public class MapValueTest {

    public static void main(String[] args) {
        //test1();
        test2();
        test3();
    }

    private static void test3() {
        Map<String, String> map = new HashMap<String,String>();
        Object o = "sdf";
        map.put("1", o.toString());
        o = "sdf222";
        map.put("2", o.toString());
        System.out.println(map);
    }

    private static void test1() {
        List<Map<String, String>> list = new LinkedList<Map<String, String>>();
        Map<String, String> map = new HashMap<String,String>();
        map.put("id","2");
        map.put("name","test");
        list.add(map);

        Map<String, String> map2 = map;
        map2.put("name","test2");
        list.add(map2);
        String test = map2.get("name");
        map2.put("name","test3");
        System.out.println(test);


        System.out.println(list);
    }

    private static void test2() {
        Map<String, String> map2 = new HashMap<String,String>();
        map2.put("name","test2");
        String test = map2.get("name");
        map2.put("name","test3");
        System.out.println(test);
    }


}
