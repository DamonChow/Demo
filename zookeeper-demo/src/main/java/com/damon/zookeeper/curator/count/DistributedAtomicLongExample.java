package com.damon.zookeeper.curator.count;

import com.damon.zookeeper.Constants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicLong;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

    @After
    public void close() {
        client.close();
    }

    @Test
    public void test() throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(QTY);
        for (int i = 0; i < QTY; ++i) {
            final DistributedAtomicLong count = new DistributedAtomicLong(client, PATH, new RetryNTimes(10, 10));
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
            Stat stat = client.checkExists().forPath(PATH);
            System.out.println("bytes Exists is " + (bytes != null));
            System.out.println("bytes=" + bytes[0] + ",length=" + bytes.length);
            System.out.println("string=" + new String(bytes));
            System.out.println("---------分割线---------------------");
            System.out.println("stat = " + stat);

            ByteBuffer wrapper = ByteBuffer.wrap(bytes);
            System.out.println("long=" + wrapper.getLong());

            /*System.out.println("---------分割线2---------------------");
            client.getData().forPath(PATH+1);*/


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
