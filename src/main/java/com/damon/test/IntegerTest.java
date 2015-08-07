package com.damon.test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 功能：
 *
 * Created by ZhouJW on 2015/3/5 11:31.
 */
public class IntegerTest {

    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;

    private static final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static int workerCountOf(int c)  { return c & CAPACITY; }
    private static int ctlOf(int rs, int wc) { return rs | wc; }
    public static void main(String[] args) {
        int i=3;
        System.out.println("44"+i);
        System.out.println("44"+"0"+i);

        System.out.println("RUNNING="+RUNNING);
        System.out.println("SHUTDOWN="+SHUTDOWN);
        System.out.println("STOP="+STOP);
        System.out.println("TIDYING="+TIDYING);
        System.out.println("TERMINATED="+TERMINATED);
        System.out.println(ctl.get());
        System.out.println(ctl.get()<SHUTDOWN);
        System.out.println(workerCountOf(ctl.get()));
        System.out.println();
        System.out.println(CAPACITY);
        System.out.println(CAPACITY & 1);
        System.out.println(CAPACITY | 1);

        System.out.println("--------------------------------------");

        Object o = 2;
        if (o instanceof  Integer) {
            System.out.println(Integer.parseInt(o.toString()));
        } else {
            System.out.println("nothing");
        }
    }
}
