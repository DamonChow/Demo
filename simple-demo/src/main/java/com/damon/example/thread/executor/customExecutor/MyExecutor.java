package com.damon.example.thread.executor.customExecutor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * 功能：创建MyExecutor类，并指定它继承ThreadPoolExecutor类。
 *
 * Created by damon on 2015/5/20 9:33.
 */

public class MyExecutor extends ThreadPoolExecutor {

    //2.声明一个私有的、ConcurrentHashMap类型的属性，并参数化为String和Date类，名为startTimes。
    private ConcurrentHashMap<String, Date> startTimes;

    //3.实现这个类的构造器，使用super关键字调用父类的构造器，并初始化startTime属性。
    public MyExecutor(int corePoolSize, int maximumPoolSize,
                      long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable>
            workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit,
                workQueue);
        startTimes=new ConcurrentHashMap<String, Date>();
    }

    //4.覆盖shutdown()方法。将关于已执行的任务，正在运行的任务和待处理的任务信息写入到控制台。
    // 然后，使用super关键字调用父类的shutdown()方法。
    @Override
    public void shutdown() {
        System.out.printf("MyExecutor: Going to shutdown.\n");
        System.out.printf("MyExecutor: Executed tasks: %d\n",getCompletedTaskCount());
        System.out.printf("MyExecutor: Running tasks: %d\n",getActiveCount());
        System.out.printf("MyExecutor: Pending tasks: %d\n",getQueue().size());
        super.shutdown();
    }

    //5.覆盖shutdownNow()方法。将关于已执行的任务，正在运行的任务和待处理的任务信息写入到控制台。
    // 然后，使用super关键字调用父类的shutdownNow()方法。
    @Override
    public List<Runnable> shutdownNow() {
        System.out.printf("MyExecutor: Going to immediatelyshutdown.\n");
        System.out.printf("MyExecutor: Executed tasks:%d\n",getCompletedTaskCount());
        System.out.printf("MyExecutor: Running tasks:%d\n",getActiveCount());
        System.out.printf("MyExecutor: Pending tasks:%d\n",getQueue().size());
        return super.shutdownNow();
    }

    //6.覆盖beforeExecute()方法。写入一条信息（将要执行任务的线程名和任务的哈希编码）到控制台。
    // 在HashMap中，使用这个任务的哈希编码作为key，存储开始日期。
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        System.out.printf("MyExecutor: A task is beginning: %s :%s\n",t.getName(),r.hashCode());
        startTimes.put(String.valueOf(r.hashCode()), new Date());
    }

    //7.覆盖afterExecute()方法。将任务的结果和计算任务的运行时间
    // （将当前时间减去存储在HashMap中的任务的开始时间）的信息写入到控制台。
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        Future<?> result=(Future<?>)r;
        try {
            System.out.printf("*af 1********************************\n");
            System.out.printf("MyExecutor: A task is finishing. %s\n",r.hashCode());
            System.out.printf("MyExecutor: Result: %s\n",result.get());
            Date startDate=startTimes.remove(String.valueOf(r.hashCode()));
            Date finishDate=new Date();
            long diff=finishDate.getTime()-startDate.getTime();
            System.out.printf("MyExecutor: Duration: %d\n",diff);
            System.out.printf("*af 2********************************\n");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

//8.创建一个SleepTwoSecondsTask类，它实现参数化为String类的Callable接口。实现call()方法。
// 令当前线程睡眠2秒，返回转换为String类型的当前时间。
class SleepTwoSecondsTask implements Callable<String> {
    public String call() throws Exception {
        TimeUnit.SECONDS.sleep(2);
        return new Date().toString();
    }
}

//9.实现这个例子的主类，通过创建Main类，并实现main()方法。
class Main {
    public static void main(String[] args) {

        //10.创建一个MyExecutor对象，名为myExecutor。
        MyExecutor myExecutor = new MyExecutor(2, 4, 1000, TimeUnit.
                MILLISECONDS, new LinkedBlockingDeque<Runnable>());

        //11.创建一个参数化为String类的Future对象的数列，用于存储你将提交给执行者的任务的结果对象。
        List<Future<String>> results = new ArrayList<Future<String>>();

        //12.提交10个Task对象。
        for (int i = 0; i < 10; i++) {
            SleepTwoSecondsTask task = new SleepTwoSecondsTask();
            Future<String> result = myExecutor.submit(task);
            results.add(result);
        }

        //13.使用get()方法，获取前5个任务的执行结果。将这些信息写入到控制台。
        for (int i = 0; i < 5; i++) {
            try {
                String result = results.get(i).get();
                System.out.printf("Main: Result for Task %d :%s\n", i, result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        //14.使用shutdown()方法结束这个执行者的执行。
        myExecutor.shutdown();

        //15.使用get()方法，获取后5个任务的执行结果。将这些信息写入到控制台。
        for (int i = 5; i < 10; i++) {
            try {
                String result = results.get(i).get();
                System.out.printf("Main: Result for Task %d :%s\n", i, result);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        //16.使用awaitTermination()方法等待这个执行者的完成。
        try {
            myExecutor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //17.写入一条信息表明这个程序执行的结束。
        System.out.printf("Main: End of the program.\n");
    }
}