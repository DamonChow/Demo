package com.damon.zookeeper.cluster;

import org.apache.zookeeper.*;

import java.util.List;

/**
 * 功能：
 *
 * @author Damon
 * @since 2016/3/2 11:28
 */
public class Client {
    public static void main(String[] args) throws Exception {
        ZooKeeper zk = new ZooKeeper("127.0.0.1:3181,127.0.0.2:4181,127.0.0.3:5181", 10000,
                new Watcher() {
                    public void process(WatchedEvent event) {
                        System.out.println("event: " + event.getType());
                    }
                });

        System.out.println(zk.getState());

        zk.create("/myApps", "myAppsData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.create("/myApps/App1", "App1Data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.create("/myApps/App2", "App2Data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.create("/myApps/App3", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.setData("/myApps/App3","App3Data".getBytes(), -1);

        System.out.println("---------分割线--------");
        System.out.println(zk.exists("/myApps", true));
        System.out.println(new String(zk.getData("/myApps", true, null)));

        List<String> children = zk.getChildren("/myApps", true);
        for (String child : children) {
            System.out.println(new String(zk.getData("/myApps/" + child, true, null)));
            zk.delete("/myApps/" + child,-1);
        }

        //zk.delete("/myApps",-1);

        zk.close();
    }
}
