package com.damon.example.thread.concurrentContainer.delayed;

import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 功能：线程安全的、带有延迟元素的列表
 *
 * Created by Domon Chow on 2015/5/14 17:58.
 */

//1.创建一个实现Delayed接口的Event类。
public class Event implements Delayed {

    //2.声明一个私有的、Date类型的属性startDate。
    private Date startDate;

    //3.实现这个类的构造器，并初始化它的属性。
    public Event(Date startDate) {
        this.startDate = startDate;
    }

    //4.实现compareTo()方法。它接收一个Delayed对象作为参数。返回当前对象的延期与作为参数传入对象的延期之间的差异。
    public int compareTo(Delayed o) {
        long result = this.getDelay(TimeUnit.NANOSECONDS) - o.
                getDelay(TimeUnit.NANOSECONDS);
        if (result < 0) {
            return -1;
        } else if (result > 0) {
            return 1;
        }
        return 0;
    }

    //5.实现getDelay()方法。返回对象的startDate与作为参数接收的TimeUnit的真实日期之间的差异。
    public long getDelay(TimeUnit unit) {
        Date now = new Date();
        long diff = startDate.getTime() - now.getTime();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }
}

//6.创建一个实现Runnable接口的Task类。
class Task implements Runnable {

    //7.声明一个私有的、int类型的属性id，用来存储任务的标识数字。
    private int id;

    //8.声明一个私有的、参数化为Event类的DelayQueue类型的属性queue。
    private DelayQueue<Event> queue;

    // 9.实现这个类的构造器，并初始化它的属性。
    public Task(int id, DelayQueue<Event> queue) {
        this.id = id;
        this.queue = queue;
    }

    //10.实现run()方法。首先，计算任务将要创建的事件的激活日期。添加等于对象ID的实际日期秒数。
    public void run() {
        Date now = new Date();
        Date delay = new Date();
        delay.setTime(now.getTime() + (id * 1000));
        System.out.printf("Task thread %s: ,delay is %s\n", id, delay);

//11.使用add()方法，在队列中存储100个事件。
        for (int i = 0; i < 100; i++) {
            Event event = new Event(delay);
            queue.add(event);
        }
    }
}

//12.通过创建Main类，并实现main()方法，来实现这个例子的主类。
class Main {
    public static void main(String[] args) throws Exception {

//13.创建一个参数化为Event类的DelayedQueue对象。
        DelayQueue<Event> queue = new DelayQueue<Event>();

//14.创建一个有5个Thread对象的数组，用来存储将要执行的任务。
        Thread threads[] = new Thread[5];

//15.创建5个具有不同IDs的Task对象。
        for (int i = 0; i < threads.length; i++) {
            Task task = new Task(i + 1, queue);
            threads[i] = new Thread(task);
        }

//16.开始执行前面创建的5个任务。
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

//17.使用join()方法等待任务的结束。
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }

//18.将存储在队列中的事件写入到控制台。当队列的大小大于0时，使用poll()方法获取一个Event类。
//如果它返回null，令主线程睡眠500毫秒，等待更多事件的激活。
        do {
            int counter = 0;
            Event event;
            do {
                event = queue.poll();
                if (event != null) counter++;
            } while (event != null);
            System.out.printf("At %s you have read %d events\n", new Date(), counter);
            TimeUnit.MILLISECONDS.sleep(500);
        } while (queue.size() > 0);
    }
}
