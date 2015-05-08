package com.damon.test.thread.syncBlock;

import com.damon.test.thread.factory.MyThreadFactory;
import com.damon.test.thread.factory.Task;

/**
 * 功能：线程工厂
 *
 * Created by ZhouJW on 2015/5/7 14:49.
 */
public class Main {

    public static void main(String[] args) {
      //  16.声明和创建一个Cinema对象。
        Cinema cinema=new Cinema();
  //      17.创建一个TicketOffice1对象，并且用线程来运行它。
        TicketOffice1 ticketOffice1=new TicketOffice1(cinema);
        Thread thread1=new Thread(ticketOffice1,"TicketOffice1");
  //      18.创建一个TicketOffice2对象，并且用线程来运行它。
        TicketOffice2 ticketOffice2=new TicketOffice2(cinema);
        Thread thread2=new Thread(ticketOffice2,"TicketOffice2");
//        19.启动这两个线程。
        thread1.start();
        thread2.start();
//        20.等待线程执行完成。
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        21.两个电影院的空缺数写入控制台。
        System.out.printf("Room 1 Vacancies: %d\n",cinema.getVacanciesCinema1());
        System.out.printf("Room 2 Vacancies: %d\n",cinema.getVacanciesCinema2());
    }
}
