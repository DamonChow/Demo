package com.damon.test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 功能：
 *
 * Created by ZhouJW on 2015/6/4 18:22.
 */
public class MapValueTest {

    public static void main(String[] args) {
        List<Map<String, String>> list = new LinkedList<Map<String, String>>();
        Map<String, String> map = new HashMap<String,String>();
        map.put("id","2");
        map.put("name","test");
        list.add(map);

        Map<String, String> map2 = map;
        map2.put("name","test2");
        list.add(map2);


        System.out.println(list);
    }
}
