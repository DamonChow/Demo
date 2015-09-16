package com.damon.test.thread.readWriteLock;

/**
 * 功能：
 *
 * Created by Domon Chow on 2015/5/8 15:39.
 */
public class Writer implements Runnable {
    //12.声明一个PricesInfo对象，并且实现Writer类的构造器来初始化这个对象。
    private PricesInfo pricesInfo;
    public Writer(PricesInfo pricesInfo){
        this.pricesInfo=pricesInfo;
    }

//    13.实现run()方法，它修改了三次两个价格的值，并且在每次修改之后睡眠2秒。
//    查看源代码打印帮助
    @Override
    public void run() {
        for (int i=0; i<3; i++) {
            System.out.printf("the %s times Writer: Attempt to modify the prices.\n" , i);
            double v1 = Math.random() * 10;
            double v2 = Math.random() * 8;
            pricesInfo.setPrices(v1, v2);
            System.out.printf("the %s times Writer: Prices have been modified.the p1 is %s, the p2 is %s\n"
                    , i, v1, v2);
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
