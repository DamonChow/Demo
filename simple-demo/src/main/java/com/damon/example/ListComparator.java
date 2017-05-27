package com.damon.example;

import com.damon.example.test.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 功能：
 *
 * @author Damon
 * @since 2016/3/9 14:23
 */
public class ListComparator {

    public static void main(String[] args) {
        List<Person> list = new ArrayList<Person>();
        list.add(new Person("a", 1));
        list.add(new Person("b", 3));
        list.add(new Person("3", 2));


        Collections.sort(list, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getAge()-o2.getAge();
            }
        });

        System.out.println(list);
    }
}
