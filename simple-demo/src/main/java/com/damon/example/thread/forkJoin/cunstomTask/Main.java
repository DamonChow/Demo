package com.damon.example.thread.forkJoin.cunstomTask;

import java.util.concurrent.ForkJoinPool;

/**
 * 功能：
 *
 * Created by damon on 2015/5/20 15:55.
 */

//15. 创建例子的主类通过创建一个类，名为 Main 并添加 main()方法。
public class Main {
    public static void main(String[] args) throws Exception {

//16. 创建一个10，000元素的 int array。
        int array[] = new int[10000];

//17.  创建一个 ForkJoinPool 对象，名为 pool。
        ForkJoinPool pool = new ForkJoinPool();

//18. Create a 创建一个 Task 对象来增加array的全部元素。构造函数的参数是：任务的名字 Task，array对象，和0 和10000来向这个任务表示要处整个array.
        Task task = new Task("Task", array, 0, array.length);

//19.  使用 execute() 方法发送任务给池。
        pool.invoke(task);

//20. 使用 shutdown() 方法关闭池。
        pool.shutdown();

//21. 在操控台写个信息表明程序结束。
        System.out.printf("Main: End of the program.\n");
    }
}
