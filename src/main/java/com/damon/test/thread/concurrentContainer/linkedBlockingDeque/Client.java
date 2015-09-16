package com.damon.test.thread.concurrentContainer.linkedBlockingDeque;

import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * 功能：非阻塞线程安全的列表
 *
 * Created by Domon Chow on 2015/5/14 17:35.
 */
//1.创建一个实现Runnable接口的Client类。

public class Client implements Runnable {
//2.声明一个私有的、LinkedBlockingDeque类型的、参数化为String类的属性requestList。

    private LinkedBlockingDeque requestList;
//3.实现这个类的构造器，并初始化它的属性。

    public Client(LinkedBlockingDeque requestList) {
        this.requestList = requestList;
    }
//4.实现run()方法。使用requestList对象的put()方法，每秒往列表插入5个String对象。重复这个循环3次。

    public void run() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                StringBuilder request = new StringBuilder();
                request.append(i);
                request.append(":");
                request.append(j);
                try {
                    requestList.put(request.toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("Client: %s at %s.\n", request, new Date());
            }
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("Client: End.\n");
    }
//5.创建这个例子的主类，通过实现Main类，并实现main()方法。
}

class Main {
    public static void main(String[] args) throws Exception {
//6.声明和创建参数化为String类、名为list的LinkedBlockingDeque。

        LinkedBlockingDeque<String> list = new LinkedBlockingDeque<String>(3);
//7.创建和启动一个Thread对象来执行client任务。

        Client client = new Client(list);
        Thread thread = new Thread(client);
        thread.start();
//8.使用这个列表的take()方法，每300毫秒获取列表的3个字符串(String)对象。
// 重复这个循环5次。将字符串(String)写入到控制台。

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                String request = list.take();
                System.out.printf("Main: Request: %s at %s. Size:%d\n", request, new Date(), list.size());
            }
            TimeUnit.MILLISECONDS.sleep(300);
        }
//9.写入一条信息表明程序的结束。

//查看源代码打印帮助
        System.out.printf("Main: End of the program.\n");
    }
}
