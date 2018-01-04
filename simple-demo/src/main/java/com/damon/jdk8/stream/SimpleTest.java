package com.damon.jdk8.stream;

import com.damon.vo.Person;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collector;
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

        List<Long> longList = Stream.of(new String[]{"1", "2", "3", "4"}).map(id -> Long.valueOf(id)).collect(Collectors.toList());
        System.out.println(longList);
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
     * 统计子组数据
     */
    @Test
    public void testCollectorsJoining() {
        List<Person> personList = getPerson();
        Map<String, Long> result = personList.stream()
                .collect(Collectors.groupingBy(Person::getAgeDesc, LinkedHashMap::new, Collectors.counting()));

        String collect = result.entrySet().stream()
                .sorted(Comparator.comparing(entry -> entry.getKey()))
                .map(entry -> "共有" + entry.getValue() + "个" + entry.getKey() + "的孩子")
                .collect(Collectors.joining(",", "", "。"));

        log.info("{}", collect);
        log.info("================================================================");
    }

    /**
     * 找相同年龄孩子的个数
     */
    @Test
    public void testGroupingBy2() {
        List<Person> personList = getPerson();
        log.info("personList={}", personList);
        LinkedHashMap<String, List<Integer>> collect = personList.stream()
                .collect(Collectors.groupingBy(Person::getAgeDesc, LinkedHashMap::new,
                        Collectors.mapping(person -> person.getSex(), Collectors.toList())));

        log.info("================================================================");
        log.info("collect={}", collect);
        log.info("================================================================");
        Map<String, Integer> sexMap = personList.stream()
                .collect(Collectors.groupingBy(Person::getAgeDesc, LinkedHashMap::new, Collectors.mapping(person -> person.getSex(),
                        Collectors.collectingAndThen(Collectors.toList(), item -> getSex(item)))));

        log.info("{}", sexMap);
        log.info("================================================================");
    }

    private int getSex(List<Integer> list) {
        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }

        if (list.contains(1)) {
            return 1;
        }

        return 2;
    }

    /**
     * 找最大的
     */
    @Test
    public void testMax() {
        List<Person> personList = getPerson();
        Optional<Person> collect = personList.stream().max(Comparator.comparing(Person::getId));
        log.info("max={}", collect.get());
        log.info("================================================================");

        Optional<Person> max2 = personList.stream().collect(Collectors.maxBy(Comparator.comparing(Person::getId)));
        log.info("max2={}", max2.get());
        log.info("================================================================");
    }

    @Test
    public void filter() {
        List<Integer> list = IntStream.rangeClosed(1, 9).mapToObj(index -> Integer.valueOf(index)).collect(Collectors.toList());
        log.info("{}|{}", list.size(), list);
        list = list.stream().filter(index -> index > 5).collect(Collectors.toList());
        log.info("{}|{}", list.size(), list);
        list.forEach(index -> log.info("{}", index));
    }

    @Test
    public void distinct() {
        List<String> list = Lists.newArrayList("AA", "BB", "CC", "DD", "EE", "FF", "AA", "CC", "BB");
        List<String> collect = list.stream().distinct().collect(Collectors.toList());
        log.info("list|{}|{}", list.size(), list);
        log.info("collect|{}|{}", collect.size(), collect);
    }

    private List<Person> getPerson() {
        List<Person> personList = Lists.newArrayList();
        personList.add(new Person(1L, "AA", 18, "十八岁", 1));
        personList.add(new Person(2L, "BB", 3333, "三千三百三十三岁", 1));
        personList.add(new Person(22L, "GG", 3333, "三千三百三十三岁", 2));
        personList.add(new Person(4L, "DD", 15, "十五岁", 1));
        personList.add(new Person(3L, "CC", 15, "十五岁", 2));
        personList.add(new Person(5L, "EE", 777, "七百七十七岁", 2));
        personList.add(new Person(6L, "FF", 3333, "三千三百三十三岁", 2));
        return personList;
    }

    @Test
    public void testStream() {
        List<Person> people = getPerson();
        log.info("people={}", people);

        // Accumulate names into a List
        List<String> list = people.stream().map(Person::getName).collect(Collectors.toList());
        log.info("list={}", list);

        // Accumulate names into a TreeSet
        Set<String> set = people.stream().map(Person::getName).collect(Collectors.toCollection(TreeSet::new));
        log.info("set={}", set);

        // Convert elements to strings and concatenate them, separated by commas
        String joined = people.stream().map(Person::toString).collect(Collectors.joining(", "));
        log.info("joined={}", joined);

        // Compute sum of id of person
        long ids = people.stream().collect(Collectors.summingLong(Person::getId));
        log.info("ids={}", ids);

        // Compute count of person by Age
        Map<Integer, Long> totalByAge = people.stream().collect(Collectors.groupingBy(Person::getAge, LinkedHashMap::new, Collectors.counting()));
        log.info("totalByAge={}", totalByAge);

        // Compute count of person by Age
        Map<Integer, Long> idMap = people.stream().collect(Collectors.groupingBy(Person::getAge, Collectors.summingLong(person->person.getId())));
        log.info("idMap={}", idMap);

        // Partition students into passing and failing
        Map<Boolean, List<Person>> passingFailing = people.stream().collect(Collectors.partitioningBy(s -> s.getAge() >= 20));
        log.info("passingFailing={}", passingFailing);

        String str = Stream.of("a", "b", "c").collect(Collectors.collectingAndThen(Collectors.joining(","), x -> x + "d"));
        log.info("str={}", str);

        String mapping = Stream.of("a", "b", "c").collect(Collectors.mapping(x -> x.toUpperCase(), Collectors.joining(",")));
        log.info("mapping={}", mapping);
    }

    @Test
    public void testSum() {
        String test = "2016,200;2018,300;2019,200;2017,200;2016,100;2018,600";
        String[] split = test.split(";");
        Map<String, Long> collect = Stream.of(split).map(str -> str.split(","))
                .collect(Collectors.groupingBy(array -> array[0], TreeMap::new, Collectors.summingLong(array -> Long.parseLong(array[1]))));
        log.info("结果={}", collect);
    }

}