package com.damon.example;

/**
 * Created by ASUS on 2015/1/9.
 */
public class ThreadLocalObjectTest {

    private static ThreadLocal<Student> threadLocal = new ThreadLocal<Student>(){
        @Override
        protected Student initialValue() {
            return new Student();
        }
    };

    static class MyThread implements Runnable {

        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            System.out.println("Thread " + name +"----------------");
            for (int i = 0;i<3;i++) {
                Student s = threadLocal.get();
                s.setName(name);
                threadLocal.set(s);
                System.out.println(name+"====" + s);
            }
        }
    }
    public static void main(String[] args) {
        new Thread(new MyThread()).start();
        new Thread(new MyThread()).start();
        new Thread(new MyThread()).start();
    }
}

class Student{
    int age;

    String name;

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {

        return age;
    }

    public String getName() {
        return name;
    }

    public Student(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public Student() {
    }
}
