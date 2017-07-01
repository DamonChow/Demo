package com.damon.example.collection;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Damon on 2017/5/12.
 */
public class MapTest {

    @Test
    public void testNewHashMap() {
        int initialCapacity = 1;
        Map<String, Object> map = new HashMap<String, Object>(initialCapacity);
        map.put("test", 1);
        System.out.println(map);
    }

    @Test
    public void testLinkedMap() {
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("bb", 3);
        map.put("cc", 4);
        map.put("dd", 5);
        map.put("aa", 1);

        System.out.println(map);

        System.out.println(map.keySet());
        System.out.println(map.values());
    }
}
