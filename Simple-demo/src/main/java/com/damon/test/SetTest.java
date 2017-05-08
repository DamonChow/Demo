package com.damon.test;

import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.HashSet;

import static com.google.common.collect.Sets.newHashSet;

/**
 * Created by Damon on 2017/3/23.
 */
public class SetTest {

    @Test
    public void test() {
        HashSet setA = newHashSet(1, 2, 3, 4, 5);
        HashSet setB = newHashSet(4, 5, 6, 7, 8);

        Sets.SetView union = Sets.union(setA, setB);
        System.out.println("并集:");
        for (Object integer : union)
            System.out.println(integer);

        Sets.SetView difference = Sets.difference(setA, setB);
        System.out.println("差集:");
        for (Object integer : difference)
            System.out.println(integer);

        Sets.SetView intersection = Sets.intersection(setA, setB);
        System.out.println("交集:");
        for (Object integer : intersection)
            System.out.println(integer);
    }
}
