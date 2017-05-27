package com.damon.example.test;

import com.damon.vo.Person;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import org.junit.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 功能：
 *
 * @author Damon
 * @since 2016/12/16 15:17
 */
public class ListTest {

    @Test
    public void test() {
        List<Person> preList = Lists.newArrayList(
                new Person(1l,"11",11),
                new Person(5l,"55",55)
        );

        List<Person> allList = Lists.newArrayList(
                new Person(1l,"11",11),
                new Person(2l,"22",22),
                new Person(3l,"33",33),
                new Person(4l,"44",44),
                new Person(5l,"55",55)
        );

        Set<Long> preIdList = preList.stream().map(person -> person.getId()).collect(Collectors.toSet());
        Set<Long> allIdList = allList.stream().map(person -> person.getId()).collect(Collectors.toSet());
        Set<Long> sameIdList= Sets.newHashSet(preIdList);
        sameIdList.retainAll(allIdList);

        allList.stream().filter(person -> sameIdList.contains(person.getId())).forEach(person -> person.setSelected(Boolean.TRUE));

        allList.addAll(preList.stream().filter(person -> !sameIdList.contains(person.getId()))
                .map(person -> {
                    person.setSelected(Boolean.TRUE);
                    return person;
                }).collect(Collectors.toList()));

        System.out.println(allList);
    }
}
