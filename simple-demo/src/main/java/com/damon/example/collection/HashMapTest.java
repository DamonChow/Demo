package com.damon.example.collection;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Damon on 2017/5/12.
 */
public class HashMapTest {

    @Test
    public void testNewHashMap() {
        int initialCapacity = 1;
        Map<String, Object> map = new HashMap<String, Object>(initialCapacity);
        map.put("test", 1);
        System.out.println(map);
    }
}
