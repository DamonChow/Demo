package com.damon.example.thread.syncMethod;

/**
 * 功能：同步方法
 *
 * 在这个指南中，我们将学习在Java中如何使用一个最基本的同步方法，
 * 即使用 synchronized关键字来控制并发访问方法。
 * 只有一个执行线程将会访问一个对象中被synchronized关键字声明的方法。
 * 如果另一个线程试图访问同一个对象中任何被synchronized关键字声明的方法，
 * 它将被暂停，直到第一个线程结束方法的执行。

 换句话说，每个方法声明为synchronized关键字是一个临界区，Java只允许一个对象执行其中的一个临界区。

 静态方法有不同的行为。只有一个执行线程访问被synchronized关键字声明的静态方法，
 但另一个线程可以访问该类的一个对象中的其他非静态的方法。 你必须非常小心这一点，
 因为两个线程可以访问两个不同的同步方法，如果其中一个是静态的而另一个不是。
 如果这两种方法改变相同的数据,你将会有数据不一致 的错误。

 为了学习这个概念，我们将实现一个有两个线程访问共同对象的示例。
 我们将有一个银行帐户和两个线程：其中一个线程将钱转移到帐户而另一个线程将从账户中扣款。
 在没有同步方法，我们可能得到不正确的结果。同步机制保证了账户的正确。
 *
 * Created by damon on 2015/5/7 14:49.
 */
public class Main {

    public static void main(String[] args) {
        Account account = new Account();
        account.setBalance(1000);
        //13.创建一个Company对象，并且用一个线程来运行它。

        Company company = new Company(account);
        Thread companyThread = new Thread(company);
        //14.创建一个Bank对象，并且用一个线程来运行它。

        Bank bank = new Bank(account);
        Thread bankThread = new Thread(bank);
        //15.在控制台打印balance初始值。

        System.out.printf("Account : Initial Balance: %f\n", account.getBalance());
        //启动这些线程。
        companyThread.start();
        bankThread.start();
//        16.等待两个使用join()方法结束的线程，并且在控制台打印账户的最终余额（balance值）。

        //      查看源代码打印帮助
        try {
            companyThread.join();
            bankThread.join();
            System.out.printf("Account : Final Balance: %f\n", account.getBalance());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
