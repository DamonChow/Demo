package com.damon.test.thread.forkJoin.cunstomTask;

import java.util.Date;
import java.util.concurrent.ForkJoinTask;

/**
 * 功能：创建一个类，名为 MyWorkerTask，并特别扩展 ForkJoinTask 类参数化 Void type。
 *
 * Created by Domon Chow on 2015/5/20 15:53.
 */
public abstract class MyWorkerTask extends ForkJoinTask<Void> {

    //2.   声明一个私有 String 属性，名为 name，用来储存任务的名字。
    private String name;

    //3.   实现类的构造函数，初始化它的属性。
    public MyWorkerTask(String name) {
        this.name = name;
    }

    //4.   实现 getRawResult() 方法。这是 ForkJoinTask 类的抽象方法之一。由于任务不会返回任何结果，此方法返回的一定是null值。
    @Override
    public Void getRawResult() {
        return null;
    }

    //5.   实现 setRawResult() 方法。这是 ForkJoinTask 类的另一个抽象方法。由于任务不会返回任何结果，方法留白即可。
    @Override
    protected void setRawResult(Void value) {

    }

//6.   实现 exec() 方法。这是任务的主要方法。在这个例子，把任务的算法委托给 compute() 方法。计算方法的运行时间并写入操控台。

    @Override
    protected boolean exec() {
        Date startDate = new Date();
        compute();
        Date finishDate = new Date();
        long diff = finishDate.getTime() - startDate.getTime();
        System.out.printf("MyWorkerTask: %s : %d Milliseconds to complete.\n", name, diff);
        return true;
    }

    //7.	实现 getName() 方法来返回任务的名字。
    public String getName() {
        return name;
    }

    //8.   声明抽象方法 compute()。像我们之前提到的，此方法实现任务的算法，必须是由 MyWorkerTask 类的子类实现。
    protected abstract void compute();
}
