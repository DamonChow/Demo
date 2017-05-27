package com.damon.example.thread.readWriteLock;

/**
 * 功能：线程工厂
 *
 * Created by Domon Chow on 2015/5/7 14:49.
 */
public class Main {

    public static void main(String[] args) {
//        15.创建一个PricesInfo对象。
        PricesInfo pricesInfo=new PricesInfo();
//        16.创建5个Reader对象，并且用5个线程来执行它们。
        Reader readers[]=new Reader[5];
        Thread threadsReader[]=new Thread[5];
        for (int i=0; i<5; i++){
            readers[i]=new Reader(pricesInfo);
            threadsReader[i]=new Thread(readers[i]);
        }
//        17.创建一个Writer对象，并且用线程来执行它。

        Writer writer=new Writer(pricesInfo);
        Thread threadWriter=new Thread(writer);
//        18.启动这些线程。
//        查看源代码打印帮助
        for (int i=0; i<5; i++){
            threadsReader[i].start();
        }
        threadWriter.start();

    }
}
