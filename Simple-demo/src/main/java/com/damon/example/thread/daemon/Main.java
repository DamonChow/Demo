package com.damon.example.thread.daemon;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 功能：守护线程
 *
 * Created by Domon Chow on 2015/5/7 14:49.
 */
public class Main {

    public static void main(String[] args) {
        Deque<Event> deque = new ArrayDeque<Event>(16);
        WriterTask writer=new WriterTask(deque);
        for (int i=0; i<3; i++){
            Thread thread=new Thread(writer);
            thread.start();
        }
        CleanerTask cleaner=new CleanerTask(deque);
        cleaner.start();

    }
}
