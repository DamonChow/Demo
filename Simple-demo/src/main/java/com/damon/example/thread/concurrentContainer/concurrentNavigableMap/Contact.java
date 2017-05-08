package com.damon.example.thread.concurrentContainer.concurrentNavigableMap;

import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 功能：
 *
 * Created by Domon Chow on 2015/5/15 10:34.
 */

//1.创建一个Contact类。
public class Contact {

    //    2.声明两个私有的、String类型的属性name和phone。
    private String name;
    private String phone;

    //    3.实现这个类的构造器，并初始化它的属性。
    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    //    4.实现返回name和phone属性值的方法。
    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}

//    5.创建一个Task类，并指定它实现Runnable接口。
class Task implements Runnable {

    //        6.声明一个私有的、参数化为String类和Contact类的ConcurrentSkipListMap类型的属性map。
    private ConcurrentSkipListMap<String, Contact> map;

    //        7.声明一个私有的、String类型的属性id，用来存储当前任务的ID。
    private String id;

    //                8.实现这个类的构造器，用来存储它的属性。
    public Task(ConcurrentSkipListMap<String, Contact> map, String id) {
        this.id = id;
        this.map = map;
    }

    //        9.实现run()方法。使用任务的ID和创建Contact对象的增长数，
    // 在map中存储1000个不同的通讯录。使用put()方法添加通讯录到map中。
    public void run() {
        for (int i = 0; i < 1000; i++) {
            Contact contact = new Contact(id, String.valueOf(i + 1000));
            map.put(id + contact.getPhone(), contact);
        }
    }
}

//        10.通过创建Main类，并添加main()方法来实现这个例子的主类。
class Main {

    public static void main(String[] args) {

//                11.创建一个参数化为String类和Contact类的ConcurrentSkipListMap对象map。
        ConcurrentSkipListMap<String, Contact> map;
        map = new ConcurrentSkipListMap<String, Contact>();

//                12.创建一个有25个Thread对象的数组，用来存储你将要执行的所有任务。
        Thread threads[] = new Thread[25];
        int counter = 0;

//                13.创建和启动25个任务，对于每个任务指定一个大写字母作为ID。
        for (char i = 'A'; i < 'Z'; i++) {
            Task task = new Task(map, String.valueOf(i));
            threads[counter] = new Thread(task);
            threads[counter].start();
            counter++;
        }

//                14.使用join()方法等待线程的结束。
        for (int i = 0; i < 25; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//                15.使用firstEntry()方法获取map的第一个实体，并将它的数据写入到控制台。
        System.out.printf("Main: Size of the map: %d\n", map.size());
        Map.Entry<String, Contact> element;
        Contact contact;
        element = map.firstEntry();
        contact = element.getValue();
        System.out.printf("Main: First Entry: name is %s: phone is %s\n", contact.
                getName(), contact.getPhone());

//                16.使用lastEntry()方法获取map的最后一个实体，并将它的数据写入到控制台。
        element = map.lastEntry();
        contact = element.getValue();
        System.out.printf("Main: Last Entry: name is %s: phone is %s\n", contact.
                getName(), contact.getPhone());

//                17.使用subMap()方法获取map的子map，并将它们的数据写入到控制台。
        System.out.printf("Main: Submap from A1996 to B1002: \n");
        ConcurrentNavigableMap<String, Contact> submap = map.
                subMap("A1996", "B1002");
        do {
            element = submap.pollFirstEntry();
            if (element != null) {
                contact = element.getValue();
                System.out.printf("%s: %s\n", contact.getName(), contact.
                        getPhone());
            }
        } while (element != null);
    }
}