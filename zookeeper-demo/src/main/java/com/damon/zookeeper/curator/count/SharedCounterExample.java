package com.damon.zookeeper.curator.count;

import com.damon.zookeeper.Constants;
import com.google.common.collect.Lists;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.shared.SharedCount;
import org.apache.curator.framework.recipes.shared.SharedCountListener;
import org.apache.curator.framework.recipes.shared.SharedCountReader;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 功能：
 *
 * @author Damon
 * @since 2015/11/9 16:33
 */
public class SharedCounterExample implements SharedCountListener {

    private static final int QTY = 5;

    private static final String PATH = "/examples/counter/int";

    public static void main(String[] args) throws Exception {
        final Random rand = new Random();
        SharedCounterExample example = new SharedCounterExample();
        CuratorFramework client = CuratorFrameworkFactory.newClient(Constants.HOST_AND_PORT,
                new ExponentialBackoffRetry(1000, 3));
        client.start();

        SharedCount statusCount = new SharedCount(client, PATH, 0);
        statusCount.addListener(example);
        statusCount.start();

        List<SharedCount> examples = Lists.newArrayList();
        ExecutorService service = Executors.newFixedThreadPool(QTY);
        for (int i = 0; i < QTY; ++i) {
            final SharedCount count = new SharedCount(client, PATH, 0);
            examples.add(count);
            Callable<Void> task = new Callable<Void>() {

                public Void call() throws Exception {
                    count.start();
                    Thread.sleep(rand.nextInt(10000));
                    System.out.println("Increment:" +
                            count.trySetCount(count.getVersionedValue(), count.getCount() + rand.nextInt(10)));
                    return null;
                }
            };
            service.submit(task);
        }
        service.shutdown();
        service.awaitTermination(10, TimeUnit.MINUTES);

        for (int i = 0; i < QTY; ++i) {
            examples.get(i).close();
        }
        statusCount.close();
        client.close();
    }

    @Test
    public void getData() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient(Constants.HOST_AND_PORT,
                new ExponentialBackoffRetry(1000, 3));
        client.start();

        byte[] bytes = client.getData().forPath(PATH);
        System.out.println("bytes Exists is " + (bytes != null));
        System.out.println("bytes=" + bytes[0] + ",length=" + bytes.length);
        System.out.println("int=" + ByteBuffer.wrap(bytes).getInt());
        client.close();
    }


    @Override
    public void countHasChanged(SharedCountReader sharedCountReader, int newCount) throws Exception {
        System.out.println("Counter's value is changed to " + newCount);
    }

    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
        System.out.println("State changed: " + connectionState.toString());
    }
}
