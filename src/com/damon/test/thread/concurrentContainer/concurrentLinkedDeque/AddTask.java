package com.damon.test.thread.concurrentContainer.concurrentLinkedDeque;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 功能：创建一个实现Runnable接口的AddTask类：
 *
 * 非阻塞线程安全的列表   ConcurrentLinkedDeque
 *
 * Created by ZhouJW on 2015/5/14 17:15.
 */

public class AddTask implements Runnable {
    //2.声明一个私有的、ConcurrentLinkedDeque类型的、参数化为String类的属性list。
    private ConcurrentLinkedDeque<String> list;
//3.实现这个类的构造器，并初始化它的属性。

    public AddTask(ConcurrentLinkedDeque<String> list) {
        this.list = list;
    }
//4.实现这个类的run()方法。它将在列表中存储10000个正在执行任务的线程的名称和一个数字的字符串。

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        for (int i = 0; i < 10000; i++) {
            list.add(name + ": Element " + i);
        }
    }
}
//5.创建一个实现Runnable接口的PollTask类。

class PollTask implements Runnable {
//6.声明一个私有的、ConcurrentLinkedDeque类型的、参数化为String类的属性list。

    private ConcurrentLinkedDeque<String> list;
//7.实现这个类的构造器，并初始化它的属性。

    public PollTask(ConcurrentLinkedDeque<String> list) {
        this.list = list;
    }
//8.实现这个类的run()方法。它从列表中取出10000个元素（在一个循环5000次的循环中，每次取出2个元素）。

    @Override
    public void run() {
        for (int i = 0; i < 5000; i++) {
            list.pollFirst();
            list.pollLast();
        }
    }
}
//9.实现这个例子的主类，通过实现Main类，并实现main()方法。

class Main {
    public static void main(String[] args) {
//10.创建一个参数化为String、名为list的ConcurrentLinkedDeque对象。

        ConcurrentLinkedDeque<String> list = new ConcurrentLinkedDeque<>();
//11.创建一个存储100个Thread对象的数组threads。

        Thread threads[] = new Thread[100];
//12.创建100个AddTask对象，对于它们中的每一个用一个线程来运行。
// 用之前创建的数组来存储每个线程，并启动这些线程。

        for (int i = 0; i < threads.length; i++) {
            AddTask task = new AddTask(list);
            threads[i] = new Thread(task);
            threads[i].start();
        }
        System.out.printf("Main: %d AddTask threads have been launched\n", threads.length);
//13.使用join()方法，等待这些线程的完成。

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//14.将列表的大小写入控制台。

        System.out.printf("Main: Size of the List: %d\n", list.size());
//15.创建100个PollTask对象，对于它们中的每一个用一个线程来运行。用之前创建的数组来存储每个线程，并启动这些线程。

        for (int i = 0; i < threads.length; i++) {
            PollTask task = new PollTask(list);
            threads[i] = new Thread(task);
            threads[i].start();
        }
        System.out.printf("Main: %d PollTask threads have been launched\n", threads.length);
//16.使用join()方法，等待这些线程的完成。

        for (int i = 0; i < threads.length; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//17.将列表的大小写入控制台。

//查看源代码打印帮助
        System.out.printf("Main: Size of the List: %d\n", list.size());
    }
}
