package com.damon.example.thread.executor.customScheduledTask;

import java.util.Date;
import java.util.concurrent.*;

/**
 * 功能：创建一个类，名为 MyScheduledTask，使名为 V 的泛型类型参数化。
 * 它扩展 FutureTask 类并实现 RunnableScheduledFuture 接口。
 *
 * Created by damon on 2015/5/20 11:33.
 */
public class MyScheduledTask<V> extends FutureTask<V> implements
        RunnableScheduledFuture<V> {

    //2.   声明一个私有 RunnableScheduledFuture 属性，名为 task.
    private RunnableScheduledFuture<V> task;

    //3.   声明一个私有 ScheduledThreadPoolExecutor，名为 executor.
    private ScheduledThreadPoolExecutor executor;

    //4.   声明一个私有long属性，名为 period。
    private long period;

    //5.   声明一个私有long属性，名为 startDate。
    private long startDate;

    //6.   实现类的构造函数。它接收任务：将要运行的 Runnable 对象，
    // 任务要返回的 result，将被用来创建 MyScheduledTask 对象的 RunnableScheduledFuture 任务，和要执行这个任务的 ScheduledThreadPoolExecutor 对象。 调用它的父类的构造函数并储存任务和执行者属性。
    public MyScheduledTask(Runnable runnable, V result,
                           RunnableScheduledFuture<V> task, ScheduledThreadPoolExecutor executor) {
        super(runnable, result);
        this.task = task;
        this.executor = executor;
    }

    //7.	实现 getDelay() 方法。如果是周期性任务且 startDate 形象的值非0，
    // 计算并返回 startDate 属性与当前日期的相差值。否则，返回储存在 task 属性的原先任务的延迟值。不要忘记你要返回结果时，要传递 time unit 作为参数哦。
    @Override
    public long getDelay(TimeUnit unit) {
        if (!isPeriodic()) {
            return task.getDelay(unit);
        } else {
            if (startDate == 0) {
                return task.getDelay(unit);
            } else {
                Date now = new Date();
                long delay = startDate - now.getTime();
                return unit.convert(delay, TimeUnit.MILLISECONDS);
            }
        }
    }

    //8.  实现 compareTo() 方法。调用原先任务的 compareTo() 方法。
    @Override
    public int compareTo(Delayed o) {
        return task.compareTo(o);
    }

    //9.  实现 isPeriodic() 方法。调用原来任务的 isPeriodic() 方法。
    @Override
    public boolean isPeriodic() {
        return task.isPeriodic();
    }

    //10. 实现方法 run()。如果这是一个周期性任务，
    // 你要用下一个执行任务的开始日期更新它的 startDate 属性。用当前日期和时间间隔的和计算它。
    // 然后，把再次把任务添加到 ScheduledThreadPoolExecutor 对象的 queue中。
    @Override
    public void run() {
        if (isPeriodic() && (!executor.isShutdown())) {
            Date now = new Date();
            startDate = now.getTime() + period;
            executor.getQueue().add(this);
        }

//11.打印当前日期的信息到操控台，调用 runAndReset() 方法运行任务，然后再打印另一条关于当前日期的信息到操控台。
        System.out.println("b s==========================================");
        System.out.printf("Pre-MyScheduledTask: %s\n", new Date());
        System.out.printf("MyScheduledTask: Is Periodic:%s\n", isPeriodic());
        super.runAndReset();
        System.out.printf("Post-MyScheduledTask: %s\n", new Date());
        System.out.println("b e==========================================");
    }

    //12. 实现 setPeriod() 方法，来确立任务的周期时间。
    public void setPeriod(long period) {
        this.period = period;
    }
}

//13. 创建一个类，名为 MyScheduledThreadPoolExecutor
// 来实现一个运行 MyScheduledTask 任务的 ScheduledThreadPoolExecutor 对象。
// 特别扩展 ScheduledThreadPoolExecutor 类。
class MyScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor {

    //14. 实现类的构造函数，只要调用它的父类的构造函数。
    public MyScheduledThreadPoolExecutor(int corePoolSize) {
        super(corePoolSize);
    }

    //15. 实现方法 decorateTask()。
    // 它接收将要被运行的 Runnable 对象和将运行 Runnable 对象的 RunnableScheduledFuture 任务作为参数。
    // 使用这些对象来构造来创建并返回 MyScheduledTask 任务。
    @Override
    //译者：前面那个<V>是打错吧多余的吧？
    protected <V> RunnableScheduledFuture<V> decorateTask(Runnable runnable,
                                                          RunnableScheduledFuture<V> task) {
        MyScheduledTask<V> myTask = new MyScheduledTask<V>(runnable, null, task, this);
        return myTask;
    }

//16. 覆盖方法 scheduledAtFixedRate()。调用它的父类的方法，调用它的父类的方法，

    @Override
//译者：不知道怎么出现？号的。应该是V。
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay,
                                                  long period, TimeUnit unit) {
        ScheduledFuture<?> task = super.scheduleAtFixedRate(command, initialDelay, period, unit);
        MyScheduledTask<?> myTask = (MyScheduledTask<?>) task;
        myTask.setPeriod(TimeUnit.MILLISECONDS.convert(period, unit));
        return task;
    }
}

//17.  创建一个类，名为 Task，实现 Runnable 接口。
class Task implements Runnable {

    //18. 实现方法 run() 。在任务开始时打印一条信息，再让当前线程进入休眠2秒。
    // 最后在任务结束时，再打印另一条信息。
    @Override
    public void run() {
        System.out.printf("%s Task: Begin.\n", this);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s Task: End.\n", this);
    }
}

//19. 创建例子的主类通过创建一个类，名为 Main 并添加 main()方法。
class Main {

    public static void main(String[] args) throws Exception {

//20. 创建一个 MyScheduledThreadPoolExecutor 对象，名为 executor。使用2作为参数来在池中获得2个线程。
        MyScheduledThreadPoolExecutor executor = new MyScheduledThreadPoolExecutor(2);

//21. 创建 Task 对象，名为 task。把当前日期写入操控台。
        Task task = new Task();
        System.out.printf("Main: %s\n", new Date());

//22. 使用 schedule() 方法发送一个延迟任务给执行者。此任务在延迟2秒后运行。
        executor.schedule(task, 2, TimeUnit.SECONDS);

//23. 让主线程休眠5秒。
        TimeUnit.SECONDS.sleep(5);

//24. 创建另一个 Task 对象。再次在操控台打印当前日期。
        task = new Task();
        System.out.printf("Main: %s\n", new Date());

//25. 使用方法 scheduleAtFixedRate()发送一个周期性任务给执行者。此任务在延迟2秒后被运行，然后依次每4秒执行。
        ScheduledFuture<?> scheduledFuture = executor.scheduleAtFixedRate(task, 1, 4, TimeUnit.SECONDS);

//26. 让主线程休眠10秒。
        TimeUnit.SECONDS.sleep(10);
        System.out.printf("Main sleep is over and executor is shutdown: %s\n", new Date());

//27. 使用 shutdown() 方法关闭执行者。使用 awaitTermination() 方法等待执行者的完结。
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.DAYS);

//28. 写信息到操控台表明任务结束。
        System.out.printf("Main: End of the program.\n");
    }
}
