package com.damon.example.thread.concurrentContainer.atomicArray;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 功能：
 *
 * Created by Domon Chow on 2015/5/15 10:51.
 */
//1.创建一个类，名为 Incrementer，并实现 Runnable 接口。
public class Incrementer implements Runnable {

    //2.声明一个私有 AtomicIntegerArray 属性，名为 vector，用来储存一个整数 array。
    private AtomicIntegerArray vector;

    //3.实现类的构造函数，初始化它的属性值。
    public Incrementer(AtomicIntegerArray vector) {
        this.vector = vector;
    }

    //4.实现 run() 方法。使用 getAndIncrement() 方操作array里的所有元素。
    @Override
    public void run() {
        for (int i = 0; i < vector.length(); i++) {
            vector.getAndIncrement(i);
        }
    }
}

//5.创建一个类，名为 Decrementer，并实现 Runnable 接口。
class Decrementer implements Runnable {

    //6.声明一个私有 AtomicIntegerArray 属性，名为 vector，用来储存一个整数 array。
    private AtomicIntegerArray vector;

    //7.实现类的构造函数，初始化它的属性值。
    public Decrementer(AtomicIntegerArray vector) {
        this.vector = vector;
    }

    //8.实现 run() 方法。使用 getAndDecrement() 方法操作array里的所有元素。
    @Override
    public void run() {
        for (int i = 0; i < vector.length(); i++) {
            vector.getAndDecrement(i);
        }
    }
}

//9.我们创建一个示例来进行示范，创建一个类，名为 Main 并添加 main()方法。
class Main {
    public static void main(String[] args) {

//10.声明一个常量，名为 THREADS，分配它的值为 100。创建一个有1，000个元素的 AtomicIntegerArray 对象。
        final int THREADS = 100;
        AtomicIntegerArray vector = new AtomicIntegerArray(1000);

//11. 创建一个 Incrementer 任务来操作之前创建的原子 array。
        Incrementer incrementer = new Incrementer(vector);

//12.创建一个 Decrementer 任务来操作之前创建的原子 array。
        Decrementer decrementer = new Decrementer(vector);

//13.创建2个array 分别存储 100 个Thread 对象。
        Thread threadIncrementer[] = new Thread[THREADS];
        Thread threadDecrementer[] = new Thread[THREADS];

//14.创建并运行 100 个线程来执行 Incrementer 任务和另外 100 个线程来执行 Decrementer 任务。把线程储存入之前创建的arrays内。
        for (int i = 0; i < THREADS; i++) {
            threadIncrementer[i] = new Thread(incrementer);
            threadDecrementer[i] = new Thread(decrementer);

            threadIncrementer[i].start();
            threadDecrementer[i].start();
        }
//15.使用 join() 方法来等待线程的完结。
        for (int i = 0; i < 100; i++) {
            try {
                threadIncrementer[i].join();
                threadDecrementer[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//16.把原子array里非0的元素写入操控台。使用 get() 方法来获取原子 array 元素。
        for (int i = 0; i < vector.length(); i++) {
            if (vector.get(i) != 0) {
                System.out.println("Vector[" + i + "] : " + vector.get(i));
            }
        }

//17.在操控台写个信息表明例子结束。
        System.out.println("Main: End of the example");
    }
}