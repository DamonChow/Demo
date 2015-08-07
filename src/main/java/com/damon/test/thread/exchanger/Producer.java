package com.damon.test.thread.exchanger;

import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * 功能：
 *
 * Created by ZhouJW on 2015/5/11 11:06.
 */
//1. 首先，从实现producer开始吧。创建一个类名为Producer并一定实现 Runnable 接口。
public class Producer implements Runnable {

    // 2. 声明 List<String>对象，名为 buffer。这是等等要被相互交换的数据类型。
    private List<String> buffer;

    // 3. 声明 Exchanger<List<String>>; 对象，名为exchanger。这个 exchanger 对象是用来同步producer和consumer的。
    private final Exchanger<List<String>> exchanger;

    // 4. 实现类的构造函数，初始化这2个属性。
    public Producer(List<String> buffer, Exchanger<List<String>> exchanger) {
        this.buffer = buffer;
        this.exchanger = exchanger;
    }

    // 5. 实现 run() 方法. 在方法内，实现10次交换。
    @Override
    public void run() {
        int cycle = 1;
        for (int i = 0; i < 10; i++) {
            System.out.printf("Producer: Cycle %d\n", cycle);
// 6. 在每次循环中，加10个字符串到buffer。
            for (int j = 0; j <10; j++) {
                String message = "Event " + ((i * 10) + j);
                System.out.printf("Producer: %s\n", message);
                buffer.add(message);
            }

// 7. 调用 exchange() 方法来与consumer交换数据。此方法可能会抛出InterruptedException 异常, 加上处理代码。
            try {
                buffer = exchanger.exchange(buffer);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Producer: size " + buffer.size());
            cycle++;
        }
    }
}
