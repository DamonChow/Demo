package com.damon.zookeeper.curator.node;

import com.damon.zookeeper.Constants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.nodes.PersistentEphemeralNode;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.KillSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * 功能：临时节点和持久节点
 *
 * @author Damon
 * @since 2015/11/10 14:10
 */
public class PersistentEphemeralNodeExample {
    private static final String PATH = "/examples/node/ephemeralNode";

    private static final String PATH2 = "/examples/node/persistent";

    private CuratorFramework client;

    @Before
    public void init() {
        client = CuratorFrameworkFactory.newClient(Constants.HOST_AND_PORT,
                new ExponentialBackoffRetry(1000, 3));
        client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            public void stateChanged(CuratorFramework client, ConnectionState newState) {
                System.out.println("client state:" + newState.name());

            }
        });
        client.start();

        try {
            client.delete().forPath(PATH2);
        } catch (Exception e) {
            // nothing
        }
    }

    @After
    public void close() {
        client.close();
    }

    @Test
    public void test() throws Exception {
        PersistentEphemeralNode node = null;
        try {
            //http://zookeeper.apache.org/doc/r3.2.2/api/org/apache/zookeeper/CreateMode.html
            node = new PersistentEphemeralNode(client, PersistentEphemeralNode.Mode.EPHEMERAL, PATH, "test".getBytes());
            node.start();
            node.waitForInitialCreate(3, TimeUnit.SECONDS);
            String actualPath = node.getActualPath();
            System.out.println("node " + actualPath + " value: " + new String(client.getData().forPath(actualPath)));

            client.create().forPath(PATH2, "persistent node".getBytes());
            System.out.println("node " + PATH2 + " value: " + new String(client.getData().forPath(PATH2)));
            KillSession.kill(client.getZookeeperClient().getZooKeeper(), "127.0.0.1:2181");
            System.out.println("node " + actualPath + " doesn't exist: " + (client.checkExists().forPath(actualPath) == null));
            System.out.println("node " + PATH2 + " value: " + new String(client.getData().forPath(PATH2)));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            node.close();
        }

    }
}
