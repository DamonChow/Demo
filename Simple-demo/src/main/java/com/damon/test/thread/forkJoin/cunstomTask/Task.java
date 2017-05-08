package com.damon.test.thread.forkJoin.cunstomTask;

/**
 * 功能：
 *
 * Created by Domon Chow on 2015/5/20 15:54.
 */

//9.   创建一个类，名为 Task，延伸 MyWorkerTask 类。
public class Task extends MyWorkerTask {

    //10. 声明一个私有 int 值 array，名为 array。
    private int array[];

    //10.声明两个私有的、int类型的属性start和end。
    private int start, end;

    //11. 实现类的构造函数，初始化它的属性值。
    public Task(String name, int array[], int start, int end) {
        super(name);
        this.array = array;
        this.start = start;
        this.end = end;
    }

    //12. 实现 compute() 方法。此方法通过 start 和 end 属性来决定增加array的元素块。如果元素块的元素超过100个，把它分成2部分，并创建2个Task对象来处理各个部分。再使用 invokeAll() 方法把这些任务发送给池。
    protected void compute() {
        if (end - start > 100) {
            int mid = (end + start) / 2;
            Task task1 = new Task(this.getName() + "1", array, start, mid);
            Task task2 = new Task(this.getName() + "2", array, mid, end);
            invokeAll(task1, task2);

//13.如果元素块的元素少于100，使用for循环增加全部的元素。
        } else {
            for (int i = start; i < end; i++) {
                array[i]++;
            }

//14. 最后，让正在执行任务的线程进入休眠50毫秒。
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
