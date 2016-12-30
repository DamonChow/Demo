package com.damon.test;

import com.damon.test.vo.Person;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 功能：ArrayList  为空，或者添加一个null
 * <p>
 * Created by Domon Chow on 2015/1/15 9:37.
 */
public class ArrayListTest {

    private Logger logger = LoggerFactory.getLogger(ArrayListTest.class);

    @Test
    public void test1() {
        /*List<Object> list = new ArrayList<Object>();
        list.add(1);
        Object s = list.get(0);
        if(s instanceof String) {
        System.out.println("String");
        } else if(s instanceof Integer) {
        System.out.println("Integer");
        } else {
        System.out.println("nothing");
        }
        System.out.println(s);*/

        /*try(FileWriter file = new FileWriter(".\\data\\log.txt");
            PrintWriter pw = new PrintWriter(file);) {

        } catch (IOException e) {
            e.printStackTrace();
        }*/

        System.out.println("----------------------------ge--------------");
        List<String> list3 = new ArrayList<String>();
        list3.forEach(System.out::println);
        System.out.println("----------------------------ge2--------------");
        list3.add("23");
        list3.add("23d");
        list3.add("23df");
        System.out.println(new StringBuilder().append(list3));
        /*List<String> list2 = null;
        for (String s2 : list2) {
            System.out.println(s2);
        }*/

        list3.forEach(s -> System.out.println(s));
        Collections.sort(list3, (a, b) -> b.compareTo(a));
        String reduce = list3.stream().reduce("--", (l, r) -> l + r);
        System.out.println(reduce);
        List<Integer> list = Arrays.asList(100, 200, 300, 400, 500);
        list.stream().map(a -> a * 2).forEach(System.out::println);
        System.out.println("----------------------------ge3--------------");
        Integer reduce1 = list.stream().map(a -> a * 2).reduce((l, r) -> l + r).get();
        System.out.println(reduce1);
        List<Integer> newList = list.stream().map(a -> a * 2).collect(Collectors.toList());
        newList.forEach(x -> System.out.println("new element is " + x));
        String collect = list3.stream().collect(Collectors.joining(","));
        System.out.println("Collectors.joining:" + collect);
        List<String> lowercaseNames = list3.stream().map(String::toUpperCase).collect(Collectors.toList());
        System.out.println("lowercaseNames" + lowercaseNames);


        List<Integer> nums = Lists.newArrayList(1, 1, null, 2, 3, 4, null, 5, 6, 7, 8, 9, 10);
        System.out.println("sum is:" + nums.stream().filter(num -> num != null).
                distinct().mapToInt(num -> num * 2).
                peek(System.out::println).skip(2).limit(4).sum());


        System.out.println("8%(2<<3)="+8%(2<<3));
        System.out.println("8%(2>>3)="+8%(2>>3));
        System.out.println("8%(2>>3)="+(2<<3));
    }

    @Test
    public void test2() {
        List<Person> list = new ArrayList<Person>();

        Person person1 = new Person();
        person1.setName("2个son");
        person1.setSex(1);
        person1.setId(123123);
        person1.setBirthday(new Date());

        List<Person> childList1 = new ArrayList<Person>();
        Person children1 = new Person();
        children1.setName("son1");
        children1.setSex(1);
        children1.setId(1231);
        children1.setBirthday(new Date());

        Person children2 = new Person();
        children2.setName("son2");
        children2.setSex(2);
        children2.setId(1231231);
        children2.setBirthday(new Date());
        childList1.add(children1);
        childList1.add(children2);

        person1.setChildList(childList1);

        Person person2 = new Person();
        person2.setName("1个空son");
        person2.setSex(1);
        person2.setId(123123);
        person2.setBirthday(new Date());
        List<Person> childList2 = new ArrayList<Person>();
        childList2.add(new Person());
        person2.setChildList(childList2);

        list.add(person1);
        list.add(person2);

        System.out.println("----------前-------------------");
        list.forEach(person -> System.out.println(person));
        System.out.println("----------后-------------------");
        list = list.stream().map(person -> {
            if (CollectionUtils.isNotEmpty(person.getChildList()) && person.getChildList().size() == 1
                    && person.getChildList().get(0).getName() == null) {
                person.setChildList(null);
            }
            return person;
        }).collect(Collectors.toList());
        list.forEach(person -> System.out.println(person));
    }

    @Test
    public void test3() {
        String test = "AdfsffsfAdfsdfGGDWASDAsfx";
        int i=0;
        int l=test.length();
        List<Character> list = new ArrayList<Character>();
        for (;i<l;i++) {
            list.add(test.charAt(i));
        }

        Map<Character, Integer> result = list.stream().collect(Collectors.groupingBy(c -> c, Collectors.summingInt(c -> 1)));
        System.out.println(result);

        List<Integer> nums = Lists.newArrayList(1,1,null,2,3,4,null,5,6,7,8,9,10);
        System.out.println("sum is:"+nums.stream().filter(num -> num != null).
                distinct().mapToInt(num -> num * 2).
                peek(System.out::print).skip(2).limit(4).sum());
    }


    @Test
    public void test() {
        List<Person> test = Lists.newArrayList(new Person("a"), new Person("b"), new Person("c"));
        test.forEach(person -> person.setName(person.getName()+"b"));
        test.forEach(System.out::println);
    }

    @Test
    public void testSubList() {
        List<String> list = Lists.newArrayList("a","b","c","d","e","f","h","g");

        logger.info("总长度--------------------------"+list.size());
        int sheetLength = 3;
        int sheetSize = list.size() / sheetLength + (list.size() % sheetLength == 0 ? 0 : 1);
        List<List<String>> result = new ArrayList(sheetSize);
        for (int sheetIndex = 0; sheetIndex < sheetSize; sheetIndex++) {
            int fromIndex = sheetIndex * sheetLength;
            int toIndex = (sheetIndex + 1) * sheetLength;
            toIndex = toIndex >= list.size() ? list.size() : toIndex;
            logger.info("from {} to {}.", new Object[]{fromIndex, toIndex});
            result.add(list.subList(fromIndex, toIndex));
        }
        result.stream().forEach(x -> logger.info(x+""));
    }
}