package com.damon.zookeeper.curator.simple;

import com.damon.zookeeper.curator.constan.Constants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.transaction.CuratorTransaction;
import org.apache.curator.framework.api.transaction.CuratorTransactionFinal;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

/**
 * 功能：事务Test
 *
 * @author Damon
 * @since 2015/12/3 10:18
 */
public class TransactionTest {

    private static final String PATH = "/examples/simple/transaction";

    private CuratorFramework client;

    @Before
    public void init() {
        client = CuratorFrameworkFactory.newClient(Constants.HOST_AND_PORT, new ExponentialBackoffRetry(1000, 3));
        client.start();
    }

    @After
    public void close() {
        client.close();
    }

    @Test
    public void transaction() throws Exception {
        //使用事务
        Collection<CuratorTransactionResult> results = client.inTransaction()
                .create().forPath(PATH, "some data".getBytes())
                .and().setData().forPath(PATH, "other data".getBytes())
                .and().delete().forPath("/yet/another/path")
                .and().commit(); // 最后必须要提交

        // 打印结果
        for (CuratorTransactionResult result : results) {
            System.out.println(result.getForPath() + " - " + result.getType());
        }
    }

    /*
     * 下面四个方法展示了Curator的事务的传统api
     */
    public CuratorTransaction startTransaction(CuratorFramework client) {
        //开始使用事务
        return client.inTransaction();
    }

    public CuratorTransactionFinal addCreateToTransaction(CuratorTransaction transaction) throws Exception {
        // 使用创建操作
        return transaction.create().forPath("/a/path", "some data".getBytes()).and();
    }

    public CuratorTransactionFinal addDeleteToTransaction(CuratorTransaction transaction) throws Exception {
        // 使用删除操作
        return transaction.delete().forPath("/another/path").and();
    }

    public void commitTransaction(CuratorTransactionFinal transaction) throws Exception {
        // 事务提交
        transaction.commit();
    }
}
