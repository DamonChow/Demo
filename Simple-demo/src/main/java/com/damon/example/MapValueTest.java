package com.damon.example;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 功能：
 * <p>
 * Created by Domon Chow on 2015/6/4 18:22.
 */
public class MapValueTest {

    @Test
    public void test1() {
        List<Map<String, String>> list = new LinkedList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", "2");
        map.put("name", "test");
        list.add(map);

        Map<String, String> map2 = map;
        map2.put("name", "test2");
        list.add(map2);
        String test = map2.get("name");
        map2.put("name", "test3");
        map.put("id", "4");
        System.out.println(test);
        System.out.println(list);
    }

    @Test
    public void test2() {
        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("name", "test2");
        String test = map2.get("name");
        map2.put("name", "test3");
        System.out.println(test);
    }

    @Test
    public void test3() {
        Map<String, String> map = new HashMap<String, String>();
        Object o = "sdf";
        map.put("1", o.toString());
        o = "sdf222";
        map.put("2", o.toString());
        System.out.println(map);
    }

    @Test
    public void test4() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<String> list = new LinkedList<String>();
        list.add("AA");
        list.add("BB");
        list.add("CC");
        map.put("list", list);
        map.put("id", "11");

        List<String> temp = (List<String>) map.get("list");
        temp.add("DD");
        temp.add("EE");
        temp.add("FF");
        System.out.println(list);
        System.out.println(map);
        System.out.println(temp);
    }
}
