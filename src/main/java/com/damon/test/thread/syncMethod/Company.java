package com.damon.test.thread.syncMethod;

/**
 * 功能：
 *
 * Created by Domon Chow on 2015/5/8 15:05.
 */
public class Company implements Runnable{
    private Account account;
    public Company(Account account) {
        this.account=account;
    }

    public void run() {
        for (int i=0; i<100; i++){
            account.addAmount(1000);
        }
    }


}
