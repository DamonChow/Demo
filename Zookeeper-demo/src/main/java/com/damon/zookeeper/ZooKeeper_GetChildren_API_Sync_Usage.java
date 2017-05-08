package com.damon.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * ZooKeeper API 获取子节点列表，使用同步(sync)接口。
 *
 * @author <a href="mailto:nileader@gmail.com">银时</a>
 */
public class ZooKeeper_GetChildren_API_Sync_Usage implements Watcher {
    private CountDownLatch connectedSemaphore = new CountDownLatch(1);
    private static CountDownLatch _semaphore = new CountDownLatch(1);
    private ZooKeeper zk;
    String path = "/get_children_test";

    ZooKeeper createSession(String connectString, int sessionTimeout, Watcher watcher) throws IOException {
        ZooKeeper zookeeper = new ZooKeeper(connectString, sessionTimeout, watcher);
        try {
            connectedSemaphore.await();
        } catch (InterruptedException e) {
        }
        return zookeeper;
    }

    void close() {
        try {
            if (zk != null) {
                zk.close();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * create path by sync
     */
    void createPath_sync(String path, String data, CreateMode createMode) throws IOException, KeeperException, InterruptedException {
        System.out.println("===Start to createPath.===,path=" + path);
        if (zk == null) {
            zk = this.createSession(Constants.HOST_AND_PORT, 5000, this);
        }
        zk.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE, createMode);
    }

    /**
     * Get children znodes of path and set watches
     */
    List getChildren(String path) throws KeeperException, InterruptedException, IOException {
        System.out.println("===Start to get children znodes.===");
        if (zk == null) {
            zk = this.createSession(Constants.HOST_AND_PORT, 5000, this);
        }
        return zk.getChildren(path, true);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ZooKeeper_GetChildren_API_Sync_Usage sample = new ZooKeeper_GetChildren_API_Sync_Usage();

        try {
            sample.createPath_sync(sample.path, "", CreateMode.PERSISTENT);
            sample.createPath_sync(sample.path + "/c1", "", CreateMode.PERSISTENT);
            sample.createPath_sync(sample.path + "/c2", "", CreateMode.PERSISTENT);
            List childrenList = sample.getChildren(sample.path);
            System.out.println(childrenList);
            //Add a new child znode to test watches event notify.
            sample.createPath_sync(sample.path + "/c3", "", CreateMode.PERSISTENT);
            _semaphore.await();
            //sample.createPath_sync(sample.path + "/c4", "", CreateMode.PERSISTENT);
        } catch (KeeperException e) {
            System.err.println("error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            sample.close();
        }
    }

    /**
     * Process when receive watched event
     */
    @Override
    public void process(WatchedEvent event) {
        System.out.println("Receive watched event：" + event);
        if (KeeperState.SyncConnected == event.getState()) {
            if (EventType.None == event.getType() && null == event.getPath()) {
                connectedSemaphore.countDown();
            } else if (event.getType() == EventType.NodeChildrenChanged) {
                //children list changed
                try {
                    System.out.println("children list changed" + this.getChildren(event.getPath()));
                    _semaphore.countDown();
                } catch (Exception e) {
                }
            }
        }
    }
}
