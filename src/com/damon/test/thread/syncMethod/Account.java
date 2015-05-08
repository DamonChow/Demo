package com.damon.test.thread.syncMethod;

/**
 * 功能：
 *
 * Created by ZhouJW on 2015/5/8 15:04.
 */
public class Account {
    private double balance;

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {

        return balance;
    }

    public synchronized void addAmount(double amount) {
        double tmp=balance;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tmp+=amount;
        balance=tmp;
    }

    public synchronized void subtractAmount(double amount) {
        double tmp=balance;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tmp-=amount;
        balance=tmp;
    }


}
