package com.damon.test;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 功能：
 *
 * Created by ZhouJW on 2015/4/15 11:36.
 */
public class CyclicBarrierTest {

    @Test
    public void test2324() throws Exception{
        final int N=5;
        final AtomicInteger counter=new AtomicInteger(25);
        final CyclicBarrier barrier=new CyclicBarrier(N);
        for(int i=0;i<N;i++)
            new Thread(new Runnable(){
                @Override
                public void run() {
                    while(counter.getAndDecrement()>0){
                        try {
                            System.out.println(Thread.currentThread().getName()+" done!"+ counter.get());
                            barrier.await();
                        } catch (Exception e) {}
                    }
                }
            }).start();

        do{
            System.out.println(Thread.currentThread().getName()+"---2'");
        }while(counter.get()>0);
    }
}
