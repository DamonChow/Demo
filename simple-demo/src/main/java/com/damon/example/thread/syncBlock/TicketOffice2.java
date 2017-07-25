package com.damon.example.thread.syncBlock;

/**
 * 功能：
 *
 * Created by damon on 2015/5/8 15:21.
 */
public class TicketOffice2 implements Runnable {
    //13.声明一个Cinema对象，并实现该类（类TicketOffice2）的构造器用来初始化这个对象。

    private Cinema cinema;
    public TicketOffice2 (Cinema cinema) {
        this.cinema=cinema;
    }
    //14.实现run()方法，用来模拟在两个电影院的一些操作。
//    查看源代码打印帮助
    @Override
    public void run() {
        cinema.sellTickets2(2);
        cinema.sellTickets2(4);
        cinema.sellTickets1(2);
        cinema.sellTickets1(1);
        cinema.returnTickets2(2);
        cinema.sellTickets1(3);
        cinema.sellTickets2(2);
        cinema.sellTickets1(2);
    }

}
