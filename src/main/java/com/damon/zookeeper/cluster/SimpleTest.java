package com.damon.zookeeper.cluster;

import com.damon.zookeeper.Constants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 功能：集群测试
 *
 * @author Damon
 * @since 2016/3/1 11:07
 */
public class SimpleTest {

    private static final String PATH = "/examples/simple";

    private CuratorFramework client;

    @Before
    public void init() {
        client = CuratorFrameworkFactory.newClient(Constants.CLUSTER_HOST_AND_PORT, new ExponentialBackoffRetry(1000, 3));
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
    public void testCreate() {
        try {
            client.create().creatingParentsIfNeeded().forPath(PATH, PATH.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testClient() {
        try {
            System.out.println("testClient = " + new String(client.getData().usingWatcher(new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    System.out.println("【【change::: " + event.getPath() + ","
                            + event.getState() + "," + event.getType().name()+"】】");
                }
            }).forPath(PATH)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
