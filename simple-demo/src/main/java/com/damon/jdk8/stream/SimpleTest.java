package com.damon.jdk8.stream;

import com.damon.vo.Person;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by damon on 2017/6/14.
 */
public class SimpleTest {

    @Test
    public void test() {

        System.out.println("啊哦".length());
        int count = 3;
        Stream.of(count).forEach(System.out::print);
        System.out.println();
        Stream.iterate(0, n -> n+1).limit(count).forEach(System.out::print);
        System.out.println();
        IntStream.range(0, count).forEach(System.out::print);
        System.out.println();
        List<Person> list = IntStream.range(0, count).mapToObj(index -> {
            System.out.print(index);
            if (index < 100)
                return null;
            else
                return new Person();
        }).collect(Collectors.toList());
        System.out.println(list);
        list = IntStream.range(0, count).mapToObj(index -> {
            System.out.print(index);
            if (index < 100)
                return null;
            else
                return new Person();
        }).filter(person -> person != null)
                .collect(Collectors.toList());
        System.out.println(list+",,"+list.size());

        Map<Person, Long> map = IntStream.range(0, count).mapToObj(index -> {
            System.out.print(index);
            if (index < 100)
                return null;
            else
                return new Person();
        }).filter(person -> person != null)
                .collect(Collectors.toMap(p -> p, p -> null));
        System.out.println(map + ",," + map.size());
    }

}