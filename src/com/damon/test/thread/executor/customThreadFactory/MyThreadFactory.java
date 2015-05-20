package com.damon.test.thread.executor.customThreadFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * 功能：创建一个实现ThreadFactory接口的MyThreadFactory类。
 *
 * Created by ZhouJW on 2015/5/20 10:07.
 */
public class MyThreadFactory implements ThreadFactory {

    //11.声明一个私有的、int类型的属性counter。
    private int counter;

    //12.声明一个私有的、String类型的属性prefix。
    private String prefix;

    //13.实现这个类的构造器，初始化它的属性。
    public MyThreadFactory(String prefix) {
        this.prefix = prefix;
        counter = 1;
    }

    //14.实现newThread()方法。创建一个MyThread对象并增加counter属性值。
    @Override
    public Thread newThread(Runnable r) {
        MyThread myThread = new MyThread(r, prefix + "-" + counter);
        counter++;
        return myThread;
    }
}


//1.创建一个继承Thread类的MyThread类。
class MyThread extends Thread {

    //2.声明3个私有的、Date类型的属性：creationDate、startDate和finishDate。
    private Date creationDate;

    private Date startDate;

    private Date finishDate;

    //3.实现这个类的构造器。它接收名称和要执行的Runnable对象参数。存储线程的创建日期。
    public MyThread(Runnable target, String name) {
        super(target, name);
        setCreationDate();
    }

    //4.实现run()方法。存储线程的开始时间，调用父类的run()方法，存储执行的结束时间。
    //method of the parent class, and store the finish date of the execution.
    @Override
    public void run() {
        setStartDate();
        super.run();
        setFinishDate();
    }

    //5.实现一个方法用来设置creationDate属性值。
    public void setCreationDate() {
        creationDate = new Date();
    }

    //6.实现一个方法用来设置startDate属性值。
    public void setStartDate() {
        startDate = new Date();
    }

    //7.实现一个方法用来设置finishDate属性值。
    public void setFinishDate() {
        finishDate = new Date();
    }

    //8.实现getExecutionTime()方法，用来计算线程的执行时间（结束日期与开始日期之差）。
    public long getExecutionTime() {
        return finishDate.getTime() - startDate.getTime();
    }

    //9.覆盖toString()方法，返回线程的创建日期和执行日期。
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(getName());
        buffer.append(": ");
        buffer.append(" Creation Date: ");
        buffer.append(creationDate);
        buffer.append(" : Running time: ");
        buffer.append(getExecutionTime());
        buffer.append(" Milliseconds.");
        return buffer.toString();
    }
}

//15.创建一个实现Runnable接口的MyTask类。实现run()方法，令当前线程睡眠2秒。
class MyTask implements Runnable {

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

//16.实现这个例子的主类，通过创建Main类，并实现main()方法。
class Main {

    public static void main(String[] args) throws Exception {

        //17.创建一个MyThreadFactory对象。
        MyThreadFactory myFactory = new MyThreadFactory("MyThreadFactory");

        //18.创建一个Task对象。
        MyTask task = new MyTask();

        //19.使用这个工厂的newThread()方法，创建一个MyThread对象来执行任务。
        Thread thread=myFactory.newThread(task);

        //20.启动这个线程并等待它的结束。
        thread.start();
        thread.join();


        //21.使用toString()方法，写入关于线程的信息。
        System.out.printf("Main: Thread information.\n");
        System.out.printf("%s\n", thread);
        System.out.printf("Main: End of the example.\n");
    }
}
