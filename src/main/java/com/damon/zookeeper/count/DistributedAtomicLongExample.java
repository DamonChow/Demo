package com.damon.zookeeper.count;

import com.damon.zookeeper.constan.Constants;
import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.test.TestingServer;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
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
public class DistributedAtomicLongExample {
    private static final int QTY = 5;
    private static final String PATH = "/examples/counter/long";

    private CuratorFramework client;

    @Before
    public void init() {
        client = CuratorFrameworkFactory.newClient(Constants.HOST_AND_PORT,
                new ExponentialBackoffRetry(1000, 3));
        client.start();

        try {
            //client.delete().forPath(PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() throws Exception {
        List<DistributedAtomicLong> examples = Lists.newArrayList();
        ExecutorService service = Executors.newFixedThreadPool(QTY);
        for (int i = 0; i < QTY; ++i) {
            final DistributedAtomicLong count = new DistributedAtomicLong(client, PATH, new RetryNTimes(10, 10));

            examples.add(count);
            Callable<Void> task = new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    try {
                        //Thread.sleep(rand.nextInt(1000));
                        AtomicValue<Long> value = count.increment();
                        //AtomicValue<Long> value = count.decrement();
                        //AtomicValue<Long> value = count.add((long)rand.nextInt(20));
                        System.out.println("succeed: " + value.succeeded());
                        if (value.succeeded())
                            System.out.println("Increment: from " + value.preValue() + " to " + value.postValue());
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
    public void getLong() {
        try {
            byte[] bytes = client.getData().forPath(PATH);
            System.out.println(new String(bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
