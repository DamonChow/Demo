package com.damon.example.thread.concurrentContainer.priorityBlockingQueue;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * 功能：线程安全的、带有延迟元素的列表
 *
 * Created by Domon Chow on 2015/5/14 17:58.
 */

//1.实现Event类，并指定它实现参数化为Event类的Comparable接口。
public class Event implements Comparable<Event> {

//2.声明一个私有的、int类型的属性thread，用来存储已创建事件的线程数。
    private int thread;

//3.声明一个私有的、int类型的属性priority，用来存储事件的优先级。
    private int priority;

//4.实现这个类的构造器，并初始化它的属性。
    public Event(int thread, int priority) {
        this.thread = thread;
        this.priority = priority;
    }

//5.实现getThread()方法，用来返回thread属性的值。
    public int getThread() {
        return thread;
    }

//6.实现getPriority()方法，用来返回priority属性的值。
    public int getPriority() {
        return priority;
    }

    //7.实现compareTo()方法。它接收Event作为参数，并且比较当前事件与参数的优先级。如果当前事件的优先级更大，则返回-1，如果这两个优先级相等，则返回0，如果当前事件的优先级更小，则返回1。注意，这与大多数Comparator.compareTo()的实现是相反的。
    public int compareTo(Event e) {
        if (this.priority > e.getPriority()) {
            return -1;
        } else if (this.priority < e.getPriority()) {
            return 1;
        } else {
            return 0;
        }
    }
}

//8.创建一个Task类，并指定它实现Runnable接口。
class Task implements Runnable {

// 9.声明一个私有的、int类型的属性id，用来存储任务的数字标识。
    private int id;

//10.声明一个私有的、参数化为Event类的PriorityBlockingQueue类型的属性queue，用来存储任务产生的事件。
    private PriorityBlockingQueue<Event> queue;

//11.实现这个类的构造器，并初始化它的属性。
    public Task(int id, PriorityBlockingQueue<Event> queue) {
        this.id = id;
        this.queue = queue;
    }

//12.实现run()方法。它存储100个事件到队列，使用它们的ID来标识创建事件的任务，
// 并给予不断增加的数作为优先级。使用add()方法添加事件到队列中。
    public void run() {
        for (int i = 0; i < 1000; i++) {
            Event event = new Event(id, i);
            queue.add(event);
        }
    }
}
//13.实现这个例子的主类，通过创建Main类，并实现main(）方法。

class Main {
    public static void main(String[] args) {

//14.创建一个参数化为Event类的PriorityBlockingQueue对象。
        PriorityBlockingQueue<Event> queue = new PriorityBlockingQueue<Event>();

//15.创建一个有5个Thread对象的数组，用来存储执行5个任务的线程。
        Thread taskThreads[] = new Thread[5];

///16.创建5个Task对象。存储前面创建的线程数组。
        for (int i = 0; i < taskThreads.length; i++) {
            Task task = new Task(i, queue);
            taskThreads[i] = new Thread(task);
        }

//17.启动前面创建的5个线程。
        for (int i = 0; i < taskThreads.length; i++) {
            taskThreads[i].start();
        }

//18.使用join()方法，等待这5个线程的结束。
        for (int i = 0; i < taskThreads.length; i++) {
            try {
                taskThreads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//19.将列队真实大小和存储在它里面的事件写入到控制台。使用poll()方法从队列中取出事件。
        System.out.printf("Main: Queue Size: %d\n", queue.size());
        for (int i = 0; i < taskThreads.length * 1000; i++) {
            Event event = queue.poll();
            System.out.printf("Thread %s: Priority %d\n", event.getThread(), event.getPriority());
        }

//20.将队列最后的大小写入到控制台。
        System.out.printf("Main: Queue Size: %d\n", queue.size());
        System.out.printf("Main: End of the program\n");
    }
}
