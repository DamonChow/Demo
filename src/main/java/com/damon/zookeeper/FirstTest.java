package com.damon.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 功能：
 *
 * @author Damon
 * @since 2015/10/30 13:59
 */
public class FirstTest {

    public static final String ROOT = "/root-ktv";

    public static ZooKeeper zk = null;

    @Before
    public void init() {
        try {
            zk = new ZooKeeper("localhost:2181", 30000, new Watcher() {
                // 监控所有被触发的事件
                public void process(WatchedEvent event) {
                    System.out.println("状态:" + event.getState()
                            + "，type:" + event.getType()
                            + ",warpper:" + event.getWrapper()
                            + ",path:" + event.getPath());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        // 创建一个与服务器的连接
        ZooKeeper zk = null;
        try {
            // 创建一个总的目录ktv，并不控制权限，这里需要用持久化节点，不然下面的节点创建容易出错
            zk.create(ROOT, "root-ktv".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            // 然后杭州开一个KTV ,	   PERSISTENT_SEQUENTIAL 类型会自动加上 0000000000 自增的后缀
            zk.create(ROOT + "/杭州KTV", "杭州KTV".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
            // 也可以在北京开一个,	   EPHEMERAL session 过期了就会自动删除
            zk.create(ROOT + "/北京KTV", "北京KTV".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            // 同理，我可以在北京开多个，EPHEMERAL_SEQUENTIAL  session 过期自动删除，也会加数字的后缀
            zk.create(ROOT + "/北京KTV-分店", "北京KTV-分店".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            // 我们也可以 来看看 一共监视了多少家的ktv
            List<String> ktvs = zk.getChildren(ROOT, true);
            System.out.println("ktvs===" + Arrays.toString(ktvs.toArray()));
            for (String node : ktvs) {
                // 删除节点
                System.out.println("node is " + node);
                zk.delete(ROOT + "/" + node, -1);
            }
            // 根目录得最后删除的
            zk.delete(ROOT, -1);
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getChildren() {
        // 我们也可以 来看看 一共监视了多少家的ktv
        List<String> ktvs = null;
        try {
            String root = "/";
            ktvs = zk.getChildren(root, true);
            String[] nodeArray = ktvs.toArray(new String[ktvs.size()]);
            String zNode = Arrays.toString(nodeArray);
            System.out.println("ktvs===" + zNode);

            for (String node : nodeArray) {
                System.out.println("data="+ new String(zk.getData(root+node, true, null)));
            }

            zk.close();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
