package com.damon.example.thread.exchanger;

import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * 功能：
 *
 * Created by Domon Chow on 2015/5/11 11:07.
 */
public class Consumer implements Runnable {

    // 9. 声明名为buffer的 List<String>对象。这个对象类型是用来相互交换的。
    private List<String> buffer;

    // 10. 声明一个名为exchanger的 Exchanger<List<String>> 对象。用来同步 producer和consumer。
    private final Exchanger<List<String>> exchanger;

    // 11. 实现类的构造函数，并初始化2个属性。
    public Consumer(List<String> buffer, Exchanger<List<String>> exchanger) {
        this.buffer = buffer;
        this.exchanger = exchanger;
    }

    // 12. 实现 run() 方法。在方法内，实现10次交换。
    @Override
    public void run() {
        int cycle = 1;
        for (int i = 0; i < 10; i++) {
            System.out.printf("Consumer: Cycle %d\n", cycle);

// 13. 在每次循环，首先调用exchange()方法来与producer同步。Consumer需要消耗数据。
// 此方法可能会抛出InterruptedException异常, 加上处理代码。
            try {
                buffer = exchanger.exchange(buffer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

// 14. 把producer发来的在buffer里的10字符串写到操控台并从buffer内删除，
// 留空。
            System.out.println("Consumer: size :" + buffer.size());
            for (int j = 0; j < 10; j++) {
                String message = buffer.get(0);
                System.out.println("Consumer: 消费" + message);
                buffer.remove(0);
            }
            cycle++;
        }
    }
}