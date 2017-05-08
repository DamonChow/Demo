package com.damon.zookeeper.curator.count;

import com.damon.zookeeper.Constants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 功能：
 *
 * @author Damon
 * @since 2015/11/9 17:10
 */
public class DistributedAtomicIntegerExample {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final int QTY = 5;
    private static final String PATH = "/examples/counter/integer";

    private CuratorFramework client;

    @Before
    public void init() {
        client = CuratorFrameworkFactory.newClient(Constants.HOST_AND_PORT,
                new ExponentialBackoffRetry(1000, 3));

        client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework client, ConnectionState newState) {
                log.info("newState=" + newState);
            }
        });

        client.getCuratorListenable().addListener(new CuratorListener() {
            @Override
            public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
                log.info("event1 = " + event);
            }
        });

        client.getCuratorListenable().addListener(new CuratorListener() {
            @Override
            public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
                log.info("event2 = " + event);
            }
        });


        try {
            //log.warn("--------------分割 sleep start-----------------");
            Thread.sleep(3000L);
            client.start();
            //client.delete().forPath(PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void close() {
        log.info("close all...");
        client.close();
    }

    @Test
    public void test() throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(QTY);
        for (int i = 0; i < QTY; ++i) {
            final DistributedAtomicInteger integer = new DistributedAtomicInteger(client, PATH, new RetryNTimes(10, 10));

            Callable<Void> task = new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    try {
                        AtomicValue<Integer> value = integer.increment();
                        //AtomicValue<Integer> value = integer.decrement();
                        //AtomicValue<Integer> value = integer.add((long)rand.nextInt(20));
                        log.info("succeed: " + value.succeeded());
                        if (value.succeeded())
                            log.info("Increment: from " + value.preValue() + " to " + value.postValue());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return null;
                }
            };
            service.submit(task);
        }

        service.shutdown();
        service.awaitTermination(10, TimeUnit.MINUTES);
    }

    @Test
    public void getInt() {
        try {
            log.debug("--------------分割 sleep getInt-----------------");
            Thread.sleep(3000L);
            byte[] bytes = client.getData().forPath(PATH);
            Stat stat = client.checkExists().forPath(PATH);
            log.info("bytes Exists is " + (bytes != null));
            log.info("bytes=" + bytes[0] + ",length=" + bytes.length);
            log.info("stat = " + stat);
            ByteBuffer wrapper = ByteBuffer.wrap(bytes);
            log.info("getInt=" + wrapper.getInt());
        } catch (Exception e) {
            log.error("error", e);
        }
    }
}
