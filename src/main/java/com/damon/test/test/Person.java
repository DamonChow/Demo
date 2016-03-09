package com.damon.test.test;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/3/3 11:27
 */
public class Person {
    String name;
    int age ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person(String personName) {
        name = personName;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String greet(String yourName) {
        return String.format("Hi %s, my name is %s", name, yourName);
    }

    public static void main(String[] args) {
        System.out.println( new Person("a").greet("b"));
    }
}
