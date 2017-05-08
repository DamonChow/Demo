package com.damon.example.thread.forkJoin.customForkJoinThread;

import java.util.concurrent.*;

/**
 * 功能：创建一个继承ForkJoinWorkerThread类的MyWorkerThread类。
 *
 * Created by Domon Chow on 2015/5/20 14:37.
 */
public class MyWorkerThread extends ForkJoinWorkerThread {

    //2.声明和创建一个参数化为Integer类的ThreadLocal属性，名为taskCounter。
    private static ThreadLocal<Integer> taskCounter = new ThreadLocal<Integer>();

    //3.实现这个类的构造器。
    protected MyWorkerThread(ForkJoinPool pool) {
        super(pool);
    }

    //4.重写onStart()方法。调用父类的这个方法，写入一条信息到控制台。设置当前线程的taskCounter属性值为0。
    @Override
    protected void onStart() {
        super.onStart();
        System.out.printf("MyWorkerThread %d: Initializing taskcounter.\n", getId());
        taskCounter.set(0);
    }

    //5.重写onTermination()方法。写入当前线程的taskCounter属性值到控制台。
    @Override
    protected void onTermination(Throwable exception) {
        System.out.printf("MyWorkerThread %d:%d\n", getId(), taskCounter.get());
        super.onTermination(exception);
    }

    //6.实现addTask()方法。递增taskCounter属性值。
    public void addTask() {
        int counter = taskCounter.get().intValue();
        counter++;
        taskCounter.set(counter);
    }

}

//7.创建一个实现ForkJoinWorkerThreadFactory接口的MyWorkerThreadFactory类。
class MyWorkerThreadFactory implements ForkJoinPool.ForkJoinWorkerThreadFactory {

    // 实现newThread()方法，创建和返回一个MyWorkerThread对象。
    @Override
    public ForkJoinWorkerThread newThread(ForkJoinPool pool) {
        return new MyWorkerThread(pool);
    }
}

//8.创建MyRecursiveTask类，它继承一个参数化为Integer类的RecursiveTask类。
class MyRecursiveTask extends RecursiveTask<Integer> {

    private int length = 10000;
    //9.声明一个私有的、int类型的属性array。
    private int array[];

    //10.声明两个私有的、int类型的属性start和end。
    private int start, end;

    //11.实现这个类的构造器，初始化它的属性。
    public MyRecursiveTask(int array[], int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    //12.实现compute()方法，用来合计数组中在start和end位置之间的所有元素。
    // 首先，将执行这个任务的线程转换成一个MyWorkerThread对象，
    // 然后使用addTask()方法来增长这个线程的任务计数器。
    @Override
    protected Integer compute() {
        MyWorkerThread thread = (MyWorkerThread) Thread.currentThread();
        thread.addTask();
        if ((end-start) <= length) {
            return length;
        }
        int end_ = end - length;
        MyRecursiveTask task1 = new MyRecursiveTask(array,start,end_);
        MyRecursiveTask task2 = new MyRecursiveTask(array,end_,end);
        task1.fork();
        task2.fork();
        return addResults(task1,task2);
    }

    //13.实现addResults()方法。计算和返回两个任务（接收参数）的结果的总和。
    private Integer addResults(MyRecursiveTask task1, MyRecursiveTask task2) {
        int value = task1.join() + task2.join();

        //14.令这个线程睡眠10毫秒，然后返回任务的结果。
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return value;
    }
}

//15.实现这个例子的主类，通过创建Main类，并实现main()方法。
class Main {
    public static void main(String[] args) throws Exception {

        //16.创建一个名为factory的MyWorkerThreadFactory对象。
        MyWorkerThreadFactory factory = new MyWorkerThreadFactory();

        //17.创建一个名为pool的ForkJoinPool对象，将前面创建的factory对象作为参数传给它的构造器。
        ForkJoinPool pool = new ForkJoinPool(4, factory, null, false);

        //18.创建一个大小为100000的整数数组，将所有元素初始化为值1。
        int array[] = new int[100000];
        for (int i = 0; i < array.length; i++) {
            array[i] = 1;
        }

        //19.创建一个新的Task对象，用来合计数组中的所有元素。
        MyRecursiveTask task = new MyRecursiveTask(array, 0, array.length);

        //20.使用execute()方法，将这个任务提交给池。
        pool.execute(task);

        //21.使用join()方法，等待这个任务的结束。
        task.join();

        //22.使用shutdown()方法，关闭这个池。
        pool.shutdown();

        //23.使用awaitTermination()方法，等待这个执行者的结束。
        pool.awaitTermination(1, TimeUnit.DAYS);

        //24.使用get()方法，将任务的结束写入到控制台。
        System.out.printf("Main: Result: %d\n", task.get());

        //25.写入一条信息到控制台，表明程序的结束。
        System.out.printf("Main: End of the program\n");
    }
}

