package com.damon.zookeeper.curator.simple;

import com.damon.zookeeper.curator.constan.Constants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * 功能：curator的curd测试
 *
 * @author Damon
 * @since 2015/12/3 10:26
 */
public class CrudTest {

    private static final String SIMPLE_PATH = "/examples/simple";

    private static final String PATH = "/examples/simple/crud";

    private CuratorFramework client;

    @Before
    public void init() {
        client = CuratorFrameworkFactory.newClient(Constants.HOST_AND_PORT, new ExponentialBackoffRetry(1000, 3));
        //client.getConnectionStateListenable().addListener(null);
        client.start();
    }

    @After
    public void close() throws InterruptedException {
        Thread.sleep(2000l);
        client.close();
    }

    /**
     * 取数据
     * @param path  路径
     * @return      数据
     * @throws Exception
     */
    public String getData(String path) throws Exception {
        // 注册观察者，当节点变动时触发
        /*byte[] bytes = client.getData().usingWatcher(new Watcher() {

            @Override
            public void process(WatchedEvent event) {
                System.out.println("node is changed");
            }
        }).forPath(path);*/
        byte[] bytes = client.getData().forPath(path);
        //byte[] bytes = client.getData().watched().inBackground().forPath(path);
        if (bytes == null) {
            return null;
        }
        return new String(bytes);
    }

    /**
     * 检查路径是否存在
     * @param path  路径
     * @return      true存在，false不存在
     * @throws Exception
     */
    public boolean checkExists(String path) throws Exception {
        Stat stat = client.checkExists().forPath(path);
        return stat != null;
    }

    @Test
    public void create() throws Exception {
        //根据路径和bytes创建节点
        String path = client.create().forPath(PATH, "create".getBytes());
        System.out.println("create node :" + path +
                ", check exists is " + checkExists(path) +
                ", data is " + getData(path));

        //如果需要创建父节点时
        client.create().creatingParentsIfNeeded().forPath("/examples/simple/tt/bb", "aa".getBytes());
        client.create().creatingParentContainersIfNeeded().forPath("/examples/simple/t/t/t/bbb", "aa".getBytes());
    }

    @Test
    public void createEphemeral() throws Exception {
        //根据路径和bytes创建临时节点，连接和session断后，自动删除
        String path = client.create()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(PATH + "/ephemeral", "ephemeral".getBytes());

        Thread.sleep(10000L);
        System.out.println("create ephemeral node :" + path + ", data is " +
            new String(client.getData().forPath(PATH + "/ephemeral")));
    }

    @Test
    public void createEphemeralSequential() throws Exception {
        // 数据使用保护模式
        //如果path已经存在，以CreateMode.EPHEMERAL创建节点，否则以CreateMode.EPHEMERAL_SEQUENTIAL方式创建节点。
        String path = PATH;
        //String path = PATH + "/ephemeral_sequential";
        String forPath = client.create().withProtection()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .forPath(path, "ephemeral_sequential".getBytes());
        Thread.sleep(10000L);
        System.out.println("create ephemeral node :" + forPath + ", data is " +
                new String(client.getData().forPath(forPath)));
    }

    @Test
    public void setData() throws Exception {
        // 修改路径下的数据
        Stat stat = client.setData().forPath(PATH, "setData".getBytes());
        System.out.println("stat状态数据：" + stat);
        System.out.println("data is " + getData(PATH));
    }

    @Test
    public void getData() throws Exception {
        // 获取路径下的数据
        byte[] bytes = client.getData().forPath(PATH);
        System.out.println("data is " + new String(bytes));
    }

    @Test
    public void getDataWithWatch() throws Exception {
        //使用异步事件通知
        CuratorListener listener = new CuratorListener() {
            @Override
            public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
                System.out.println("【getDataWithWatch】异步事件通知----CuratorEvent事件数据：" + event);
            }
        };
        client.getCuratorListenable().addListener(listener);
        // 获取路径下的数据
        byte[] bytes = client.getData().watched().forPath(PATH);
        System.out.println("data is " + new String(bytes));
    }

    @Test
    public void getDataBackground() throws Exception {
        //使用异步事件通知
        BackgroundCallback callback = new BackgroundCallback() {

            @Override
            public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                System.out.println("callback异步完成通知的方式-----CuratorEvent事件数据：" + event);
                System.out.println("data==" + new String(event.getData()));
            }
        };
        // 获取路径下的数据
        client.getData().inBackground(callback, "ABC").forPath(PATH);
    }


    @Test
    public void setDataAsync() throws Exception {
        //使用异步事件通知
        CuratorListener listener = new CuratorListener() {
            @Override
            public void eventReceived(CuratorFramework client, CuratorEvent event) throws Exception {
                System.out.println("异步事件通知----CuratorEvent事件数据：" + event);
            }
        };
        client.getCuratorListenable().addListener(listener);
        //通过使用CuratorListener监听完成通知，异步的更新节点数据。
        Stat stat = client.setData().inBackground().forPath(PATH, "setDataAsync".getBytes());
        System.out.println("stat状态数据：" + stat);
        System.out.println("data is " + getData(PATH));
    }

    @Test
    public void setDataAsyncWithCallback() throws Exception {
        //另外一种异步完成通知的方式
        BackgroundCallback callback = new BackgroundCallback() {

            @Override
            public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                System.out.println("callback异步完成通知的方式-----CuratorEvent事件数据：" + event);
            }
        };
        Stat stat = client.setData().inBackground(callback).forPath(PATH, "setDataAsyncWithCallback".getBytes());
        System.out.println("stat状态数据：" + stat);
    }

    @Test
    public void delete() throws Exception {
        //删除节点
        client.delete().forPath(PATH);
        System.out.println("node is exists [" + checkExists(PATH) + "]");
    }

    @Test
    public void guaranteedDelete() throws Exception {
        //删除节点并保证完成
        client.delete().guaranteed().forPath(PATH);
        System.out.println("node is exists [" + checkExists(PATH) + "]");
    }

    public List<String> watchedGetChildren() throws Exception {
        /**
         * Get children and set a watcher on the node. The watcher notification
         * will come through the CuratorListener (see setDataAsync() above).
         */
        return client.getChildren().watched().forPath(SIMPLE_PATH);
    }

    public List<String> watchedGetChildren(Watcher watcher) throws Exception {
        /**
         * Get children and set the given watcher on the node.
         */
        return client.getChildren().usingWatcher(watcher).forPath(SIMPLE_PATH);
    }
}
