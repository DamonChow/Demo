package com.damon.kafka.spring.example2;

import org.springframework.kafka.annotation.KafkaListener;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Damon on 2017/5/15.
 */
public class Listener {

    private final CountDownLatch latch1 = new CountDownLatch(1);

    @KafkaListener(id = "foo", topics = "annotated1")
    public void listen1(String foo) {
        System.out.println(foo+"-----------------");
        this.latch1.countDown();
    }

    public CountDownLatch getLatch1() {
        return latch1;
    }
}
