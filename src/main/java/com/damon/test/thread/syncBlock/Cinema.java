package com.damon.test.thread.syncBlock;

/**
 * 功能：
 *
 * Created by Domon Chow on 2015/5/8 15:17.
 */
public class Cinema {
    //1.创建一个Cinema类，添加两个long类型的属性，命名为vacanciesCinema1和vacanciesCinema2。
    private long vacanciesCinema1;
    private long vacanciesCinema2;
    //2.给Cinema类添加两个额外的Object属性，命名为controlCinema1和controlCinema2。
    private final Object controlCinema1, controlCinema2;

    public Cinema(){
        controlCinema1=new Object();
        controlCinema2=new Object();
        vacanciesCinema1=20;
        vacanciesCinema2=20;
    }

    /**
     * 4.实现sellTickets1()方法，当第一个电影院出售一些门票将调用它。
     * 使用controlCinema1对象来控制访问synchronized的代码块。
     * @param number
     * @return
     */
    public boolean sellTickets1 (int number) {
        synchronized (controlCinema1) {
            if (number<vacanciesCinema1) {
                vacanciesCinema1-=number;
                return true;
            } else {
                return false;
            }
        }
    }



    /**
     * 5.实现sellTickets2()方法，当第二个电影院出售一些门票将调用它。
     * 使用controlCinema2对象来控制访问synchronized的代码块。
     * 查看源代码打印帮助
     * @param number
     * @return
     */
    public boolean sellTickets2 (int number) {
        synchronized (controlCinema2) {
            if (number<vacanciesCinema2) {
                vacanciesCinema2-=number;
                return true;
            } else {
                return false;
            }
        }
    }

    //6.实现returnTickets1()方法，当第一个电影院被退回一些票时将调用它。
    // 使用controlCinema1对象来控制访问synchronized的代码块。
    public boolean returnTickets1 (int number) {
        synchronized (controlCinema1) {
            vacanciesCinema1+=number;
            return true;
        }
    }

    //    7.实现returnTickets2()方法，当第二个电影院被退回一些票时将调用它。
    // 使用controlCinema2对象来控制访问synchronized的代码块。
    public boolean returnTickets2 (int number) {
        synchronized (controlCinema2) {
            vacanciesCinema2+=number;
            return true;
        }
    }


    //8.实现其他两个方法，用来返回每个电影院空缺位置的数量。
    public long getVacanciesCinema1() {
        return vacanciesCinema1;
    }
    public long getVacanciesCinema2() {
        return vacanciesCinema2;
    }



}
