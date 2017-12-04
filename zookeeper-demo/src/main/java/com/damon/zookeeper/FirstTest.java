package com.damon.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
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
            zk = new ZooKeeper(Constants.HOST_AND_PORT_, 30000, new Watcher() {
                // 监控所有被触发的事件
                public void process(WatchedEvent event) {
                    System.out.println("状态:[" + event.getState()
                            + "]，type:[" + event.getType()
                            + "],warpper:[" + event.getWrapper()
                            + "],path:[" + event.getPath()+"]");
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @After
    public void close() throws InterruptedException {
        if (zk != null) {
            zk.close();
        }
    }

    @Test
    public void testT() throws KeeperException, InterruptedException {
        zk.create(ROOT, "test".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    @Test
    public void test() {
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
            //System.out.println("getData='ROOT + \"/北京KTV-分店\"'" + zk.getData(ROOT + "/北京KTV-分店", true));
            for (String node : ktvs) {
                // 删除节点
                System.out.println("node is " + node);
                zk.delete(ROOT + "/" + node, -1);
            }
            // 根目录得最后删除的
            zk.delete(ROOT, -1);
        } catch (Exception e) {
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
            System.out.println("---------------------分割----------------");
            for (String node : nodeArray) {
                System.out.println("data=" + new String(zk.getData(root + node, true, null)));
            }
            System.out.println("---------------------分割----------------");
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
