package com.damon.test.thread.syncMethod;

/**
 * 功能：
 *
 * Created by ZhouJW on 2015/5/8 15:05.
 */
public class Bank implements Runnable  {
    private Account account;
    public Bank(Account account) {
        this.account=account;
    }

    public void run() {
        for (int i=0; i<100; i++){
            account.subtractAmount(1000);
        }
    }


}
