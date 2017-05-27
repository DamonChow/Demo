package com.damon.example.thread;

import java.util.Random;

/**
 * 功能：
 *
 * Created by Domon Chow on 2015/5/7 15:37.
 */
public class MyThreadGroup extends ThreadGroup {

    public MyThreadGroup(String name) {
        super(name);
    }

    public static void main(String[] args) {
        MyThreadGroup threadGroup=new MyThreadGroup("MyThreadGroup");
        threadGroup.test(threadGroup);
    }

    void test(MyThreadGroup threadGroup) {
        Task task = new Task();
        for (int i=0; i<2; i++){
            Thread t=new Thread(threadGroup,task);
            t.start();
        }
    }

        @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.printf("The thread %s has thrown an Exception\n", t.getId());
        e.printStackTrace(System.out);
        System.out.printf("Terminating the rest of the Threads\n");
        interrupt();
    }

    class Task implements Runnable {
        public void run() {
            int result;
            Random random=new Random(Thread.currentThread().getId());
            while (true) {
                result=1000/((int)(random.nextDouble()*1000));
                System.out.printf("%s : %s\n",Thread.currentThread().getId(),result);
                if (Thread.currentThread().isInterrupted()) {
                    System.out.printf("%d : Interrupted\n",Thread.currentThread().getId());
                    return;
                }
            }
        }

    }

}
