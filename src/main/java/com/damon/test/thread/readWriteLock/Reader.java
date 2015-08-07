package com.damon.test.thread.readWriteLock;

/**
 * 功能：
 *
 * Created by ZhouJW on 2015/5/8 15:38.
 */
public class Reader implements Runnable {
    //9.声明一个PricesInfo对象，并且实现Reader类的构造器来初始化这个对象。
    private PricesInfo pricesInfo;
    public Reader (PricesInfo pricesInfo){
        this.pricesInfo=pricesInfo;
    }

//    10.实现Reader类的run()方法，它读取10次两个价格的值。
    @Override
    public void run() {
        for (int i=0; i<5; i++){
            System.out.printf("%s: Price 1: %f\n", Thread.
                    currentThread().getName(),pricesInfo.getPrice1());
            System.out.printf("%s: Price 2: %f\n", Thread.
                    currentThread().getName(),pricesInfo.getPrice2());
        }
    }

}
