package com.damon.example.thread.factory;

/**
 * 功能：线程工厂
 *
 * Created by damon on 2015/5/7 14:49.
 */
public class Main {

    public static void main(String[] args) {
        MyThreadFactory factory=new MyThreadFactory("MyThreadFactory");
        Task task=new Task();
        Thread thread;
        System.out.printf("Starting the Threads\n");
        for (int i=0; i<10; i++){
            thread=factory.newThread(task);
            thread.start();
        }
        System.out.printf("Factory stats:\n");
        System.out.printf("%s\n",factory.getStats());
    }
}
