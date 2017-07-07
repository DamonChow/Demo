package com.damon.jdk8.stream;

import com.damon.vo.Person;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by damon on 2017/6/14.
 */
@Slf4j
public class SimpleTest {

    @Test
    public void test() {

        System.out.println("啊哦".length());
        int count = 3;
        Stream.of(count).forEach(System.out::print);
        System.out.println();
        Stream.iterate(0, n -> n + 1).limit(count).forEach(System.out::print);
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
        System.out.println(list + ",," + list.size());

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

    /**
     * 多条件排序
     */
    @Test
    public void testSort() {
        List<Person> personList = getPerson();
        List<Person> collect = personList.stream()
                .sorted(Comparator.comparing(Person::getAge).thenComparing(Comparator.comparing(Person::getId)))
                .collect(Collectors.toList());

        log.info("{}", collect);
        log.info("================================================================");
    }

    /**
     * 找相同年龄孩子的个数
     */
    @Test
    public void testCollectorsJoining() {
        List<Person> personList = getPerson();
        Map<Integer, List<Person>> result = personList.stream().collect(Collectors.groupingBy(Person::getAge));
        String collect = result.entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getKey()))
                .map(entry -> {
                    int count = entry.getValue().size();
                    String ageDesc = entry.getValue().get(0).getAgeDesc();
                    return "共有" + count + "个" + ageDesc + "的孩子";
                })
                .collect(Collectors.joining(",", "", "。"));

        log.info("{}", collect);
        log.info("================================================================");
    }

    /**
     * 找最大的
     */
    @Test
    public void testMax() {
        List<Person> personList = getPerson();

        Optional<Person> collect = personList.stream().max(Comparator.comparing(Person::getId));
//                .collect(Collectors.maxBy(Comparator.comparing(Person::getId)));

        log.info("{}", collect.get());
        log.info("================================================================");
    }

    /**
     * 找最大的2
     */
    @Test
    public void testMax2() {
        List<Person> personList = getPerson();

        Optional<Person> collect = personList.stream()
                .collect(Collectors.maxBy(Comparator.comparing(Person::getId)));

        log.info("{}", collect.get());
        log.info("================================================================");
    }


    private List<Person> getPerson() {
        List<Person> personList = Lists.newArrayList();
        personList.add(new Person(1L, "AA", 18, "十八岁"));
        personList.add(new Person(2L, "BB", 3333, "三千三百三十三岁"));
        personList.add(new Person(22L, "GG", 3333, "三千三百三十三岁"));
        personList.add(new Person(4L, "DD", 15, "十五岁"));
        personList.add(new Person(3L, "CC", 15, "十五岁"));
        personList.add(new Person(5L, "EE", 777, "七百七十七岁"));
        personList.add(new Person(6L, "FF", 3333, "三千三百三十三岁"));
        return personList;
    }

}