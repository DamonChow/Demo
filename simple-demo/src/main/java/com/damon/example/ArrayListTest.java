package com.damon.example;

import com.damon.vo.Person;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * 功能：ArrayList  为空，或者添加一个null
 * <p>
 * Created by damon on 2015/1/15 9:37.
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


        System.out.println("8%(2<<3)=" + 8 % (2 << 3));
        System.out.println("8%(2>>3)=" + 8 % (2 >> 3));
        System.out.println("8%(2>>3)=" + (2 << 3));
    }

    @Test
    public void test2() {
        List<Person> list = new ArrayList<Person>();

        Person person1 = new Person();
        person1.setName("2个son");
        person1.setSex(1);
        person1.setId(123123L);
        person1.setBirthday(new Date());

        List<Person> childList1 = new ArrayList<Person>();
        Person children1 = new Person();
        children1.setName("son1");
        children1.setSex(1);
        children1.setId(1231L);
        children1.setBirthday(new Date());

        Person children2 = new Person();
        children2.setName("son2");
        children2.setSex(2);
        children2.setId(1231231L);
        children2.setBirthday(new Date());
        childList1.add(children1);
        childList1.add(children2);

        person1.setChildList(childList1);

        Person person2 = new Person();
        person2.setName("1个空son");
        person2.setSex(1);
        person2.setId(123123L);
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
        int i = 0;
        int l = test.length();
        List<Character> list = new ArrayList<Character>();
        for (; i < l; i++) {
            list.add(test.charAt(i));
        }

        Map<Character, Integer> result = list.stream().collect(Collectors.groupingBy(c -> c, Collectors.summingInt(c -> 1)));
        System.out.println(result);

        List<Integer> nums = Lists.newArrayList(1, 1, null, 2, 3, 4, null, 5, 6, 7, 8, 9, 10);
        System.out.println("sum is:" + nums.stream().filter(num -> num != null).
                distinct().mapToInt(num -> num * 2).
                peek(System.out::print).skip(2).limit(4).sum());
    }

    @Test
    public void test() {
        List<Person> test = Lists.newArrayList(new Person("a"), new Person("b"), new Person("c"));
        test.forEach(person -> person.setName(person.getName() + "b"));
        test.forEach(System.out::println);
    }

    @Test
    public void testSubList() {
        List<String> list = Lists.newArrayList("a", "b", "c", "d", "e", "f", "h", "g");

        logger.info("list = {} ", list);
        logger.info("总长度--------------------------" + list.size());
        int sheetLength = 3;
        int sheetSize = list.size() / sheetLength + (list.size() % sheetLength == 0 ? 0 : 1);
        List<List<String>> result = new ArrayList(sheetSize);
        for (int sheetIndex = 0; sheetIndex < sheetSize; sheetIndex++) {
            int fromIndex = sheetIndex * sheetLength;
            int toIndex = (sheetIndex + 1) * sheetLength;
            toIndex = toIndex >= list.size() ? list.size() : toIndex;
            logger.info("from {} to {}.", new Object[]{fromIndex, toIndex});
            logger.info("result subList = {} ", list.subList(fromIndex, toIndex));
            result.add(list.subList(fromIndex, toIndex));
        }
        result.stream().forEach(x -> logger.info(x + ""));
    }

    @Test
    public void testSubList2() {
        List<String> list = Lists.newArrayList("a", "b", "c", "d", "e", "f", "h", "g");

        logger.info("list2 = {} ", list);
        logger.info("总长度2--------------------------" + list.size());
        int sheetLength = 3;
        int sheetSize = list.size() / sheetLength + (list.size() % sheetLength == 0 ? 0 : 1);
        List<List<String>> result = new ArrayList(sheetSize);
        IntStream.range(0, sheetSize).forEach(index ->{
            int fromIndex = index * sheetLength;
            int toIndex = (index + 1) * sheetLength;
            toIndex = toIndex >= list.size() ? list.size() : toIndex;
            List<String> sub = list.stream().skip(fromIndex).limit(sheetLength).collect(Collectors.toList());
            logger.info("from {} to {}, subList = {} ", fromIndex, toIndex, sub);
            result.add(sub);
        });
        result.stream().forEach(x -> logger.info(x + ""));
    }

    @Test
    public void testRetainAll() {
        List<String> preList = Lists.newArrayList("AA", "FF", "CC", "DD");
        String[] arrayString = {"AA", "FF", "SS", "CfC"};
        preList.forEach(System.out::print);
        System.out.println("\n----------------------------");
        System.out.println(CollectionUtils.containsAny(preList, Lists.newArrayList(arrayString)));
        System.out.println("----------------------------");
        System.out.println(!Collections.disjoint(preList, Lists.newArrayList(arrayString)));
        System.out.println("----------------------------");
        List<String> tempList = filterList(preList, arrayString);
        preList.forEach(System.out::print);
        System.out.println("\n----------------------------isEmpty=" + tempList.isEmpty());
        tempList.forEach(System.out::println);
    }

    private List<String> filterList(List<String> preList, String[] arrayString) {
        List<String> tempList = Lists.newArrayList(preList);
        tempList.retainAll(Lists.newArrayList(arrayString));
        return tempList;
    }

    @Test
    public void testList() {
        List listA = Lists.newArrayList(1, 2, 3, 4, 5);
        List listB = Lists.newArrayList(4, 5, 6, 7, 8);
        printCollection(listA, "A");
        printCollection(listB, "B");

        Collection union = CollectionUtils.union(listA, listB);
        System.out.print("\n取所有元素:");
        for (Object integer : union)
            System.out.print(integer);

        Collection disjunction = CollectionUtils.disjunction(listA, listB);
        System.out.print("\n取不相同的元素:");
        for (Object integer : disjunction)
            System.out.print(integer);

        Collection intersection = CollectionUtils.intersection(listA, listB);
        System.out.print("\n交集:取相同元素");
        for (Object integer : intersection)
            System.out.print(integer);

        System.out.println("\nB是A子集？" + listA.containsAll(listB));

        boolean b = listA.retainAll(listB);
        System.out.print("\nA集和B集是否有相同元素：" + b);
        printCollection(listA, "A");
        printCollection(listB, "B");
    }

    private void printCollection(List<String> list, String listName) {
        System.out.print("\n集合" + listName + ":的元素");
        for (Object integer : list)
            System.out.print(integer);
    }

    @Test
    public void testMap2List() {
//        Map<String, List<String>> source = Maps.newHashMap("ID", Lists.newArrayList("name","sex","birthday"));
    }

    @Test
    public void testMain() {
        String[] arrayA = new String[]{"1", "2", "3", "4", "5"};
        String[] arrayB = new String[]{"3", "4", "5", "6", "7"};

        List<String> a = Arrays.asList(arrayA);
        List<String> b = Arrays.asList(arrayB);
        //并集
        Collection<String> union = CollectionUtils.union(a, b);
        //交集
        Collection<String> intersection = CollectionUtils.intersection(a, b);
        //交集的补集
        Collection<String> disjunction = CollectionUtils.disjunction(a, b);
        //集合相减
        Collection<String> subtract = CollectionUtils.subtract(a, b);
        //集合相减
        Collection<String> ba_subtract = CollectionUtils.subtract(b, a);

        Collections.sort((List<String>) union);
        Collections.sort((List<String>) intersection);
        Collections.sort((List<String>) disjunction);
        Collections.sort((List<String>) subtract);
        Collections.sort((List<String>) ba_subtract);

        System.out.println("A: " + ArrayUtils.toString(a.toArray()));
        System.out.println("B: " + ArrayUtils.toString(b.toArray()));
        System.out.println("--------------------------------------------");
        System.out.println("并集 Union(A, B): " + ArrayUtils.toString(union.toArray()));
        System.out.println("交集 Intersection(A, B): " + ArrayUtils.toString(intersection.toArray()));
        System.out.println("交集的补集 Disjunction(A, B): " + ArrayUtils.toString(disjunction.toArray()));
        System.out.println("A集合减B集合 Subtract(A, B): " + ArrayUtils.toString(subtract.toArray()));
        System.out.println("B集合减A集合 Subtract(B, A): " + ArrayUtils.toString(ba_subtract.toArray()));
    }

    @Test
    public void testAddAll() {
        String[] arrayA = new String[]{"1", "2", "3", "4", "5"};
        String[] arrayB = new String[]{"9", "6", "7"};

        List<String> a = new ArrayList(Arrays.asList(arrayA));
        List<String> a4All = new ArrayList(Arrays.asList(arrayA));
        List<String> b = new ArrayList(Arrays.asList(arrayB));

        System.out.println("a=" + a);
        System.out.println("a4All=" + a4All);
        System.out.println("b=" + b);
        System.out.println();

        List<String> result = new ArrayList();
        result.addAll(a);
        result.addAll(b);
        System.out.println("result = " + result);
        System.out.println();

        a4All.addAll(b);
        System.out.println("result = " + a4All);
    }

    @Test
    public void testSumMoreList() {
        List<String> list1 = Lists.newArrayList("1", "5", "9", "13", "17");
        List<String> list2 = Lists.newArrayList("2", "6", "10", "14", "18");
        List<String> list3 = Lists.newArrayList("3", "7", "11", "15", "19");
        List<String> list4 = Lists.newArrayList("4", "8", "12", "16", "19");

        List<List<String>> list = Lists.newArrayList(list1, list2, list3, list4);

        list.stream().collect(Collectors.toList());


        System.out.println("result = " + list);
    }

    @Test
    public void testRemoveAllList() {
        List<String> list1 = Lists.newArrayList("1", "2", "3", "13", "19");
        List<String> list4 = Lists.newArrayList("13", "19");

        list1.removeAll(list4);
        System.out.println("result = " + list1);
    }


}