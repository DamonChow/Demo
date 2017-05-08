package com.damon.example.thread.executor.priorityExecutor;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 功能：优先级的Executor类
 *
 * Created by Domon Chow on 2015/5/15 11:20.
 */

//1.创建一个MyPriorityTask类，它实现Runnable接口和参数化为MyPriorityTask类的Comparable接口。
public class MyPriorityTask implements Runnable,
        Comparable<MyPriorityTask> {

    //        2.声明一个私有的、int类型的属性priority。
    private int priority;

    //        3.声明一个私有的、String类型的属性name。
    private String name;

    //        4.实现这个类的构造器，并初始化它的属性。
    public MyPriorityTask(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    //        5.实现一个方法来返回priority属性的值。
    public int getPriority() {
        return priority;
    }

    //        6.实现声明在Comparable接口中的compareTo()方法。它接收一个MyPriorityTask对象作为参数，比较这两个对象（当前对象和参数对象）的优先级。让优先级高的任务先于优先级低的任务执行。
    public int compareTo(MyPriorityTask o) {
        if (this.getPriority() < o.getPriority()) {
            return 1;
        }
        if (this.getPriority() > o.getPriority()) {
            return -1;
        }
        return 0;
    }

    //        7.实现run()方法。令当前线程睡眠2秒。
    public void run() {
        System.out.printf("MyPriorityTask: %s Priority :%d\n", name, priority);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

//        8.实现这个例子的主类，通过创建Main类，并实现main()方法。
class Main {
    public static void main(String[] args) {

        //      9.创建一个ThreadPoolExecutor对象，名为executor。使用参数化为Runnable接口的PriorityBlockingQueue作为执行者用来存储待处理任务的队列。
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 1, TimeUnit.SECONDS,
                new PriorityBlockingQueue<Runnable>());

//        10.提交4个使用循环计数器作为优先级的任务给执行者。使用execute()方法提交这些任务给执行者。
        for (int i = 0; i < 4; i++) {
            MyPriorityTask task = new MyPriorityTask("Task " + i, i);
            executor.execute(task);
        }

//        11.令当前线程睡眠1秒。
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        12.提交4个额外的，使用循环计数器作为优先级的任务给执行者。使用execute()方法提交这些任务给执行者。
        for (int i = 4; i < 8; i++) {
            MyPriorityTask task = new MyPriorityTask("Task " + i, i);
            executor.execute(task);
        }

//        13.使用shutdown()方法关闭这个执行者。
        executor.shutdown();

//        14.使用awaitTermination()方法等待这个执行者的结束。
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        15.写入一条信息表明这个程序的结束。
        System.out.printf("Main: End of the program.\n");
    }
}