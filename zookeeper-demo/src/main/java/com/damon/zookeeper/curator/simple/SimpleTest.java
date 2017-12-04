package com.damon.zookeeper.curator.simple;

import com.damon.zookeeper.Constants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.transaction.CuratorTransaction;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

/**
 * 功能：
 *
 * @author Damon
 * @since 2015/12/1 11:05
 */
public class SimpleTest {

    private static final String PATH = "/examples/simple";

    private CuratorFramework client;

    @Before
    public void init() {
        client = CuratorFrameworkFactory.newClient(Constants.HOST_AND_PORT_, new ExponentialBackoffRetry(1000, 3));
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
            client.create().forPath(PATH, PATH.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTransaction() {
        try {
            testClient();

            // 开启事务
            CuratorTransaction transaction = client.inTransaction();
            Collection<CuratorTransactionResult> results =
                    transaction.create().forPath("/examples/simple/test", "some data".getBytes())
                            .and().setData().forPath(PATH, "other data111".getBytes())
                            //.and().delete().forPath("/yet/another/path")
                            .and().commit();
            client.setData().forPath(PATH, "other data122".getBytes());
            for (CuratorTransactionResult result : results) {
                System.out.println(result.getForPath() + " - " + result.getType());
            }
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
