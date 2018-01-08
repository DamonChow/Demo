package com.damon.core;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.List;

/**
 * 功能：
 *
 * @author Damon
 * @since 2017/12/25 15:09
 */
public class ZooKeeperFactory {

    public static final int MAX_RETRIES = 3;

    public static final int BASE_SLEEP_TIME = 3000;

    public static CuratorFramework get(String connect) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETRIES);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(connect)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
        return client;
    }

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = ZooKeeperFactory.get("192.168.0.23:2181");
        String path = "/config/dev/kjb.product/dubbo.properties";
        List<String> list = curatorFramework.getChildren().forPath(path);
        System.out.println(list);
    }

}