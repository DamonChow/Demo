package com.damon.zookeeper.curator.lock.shared.readwrite;

import com.damon.zookeeper.curator.constan.Constants;
import com.damon.zookeeper.curator.lock.shared.FakeLimitedResource;
import com.damon.zookeeper.curator.lock.shared.unreentrant.ExampleClientThatLocks;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 功能：可重入读写锁Shared Reentrant Read Write Lock
 * <p>
 *     类似JDK的ReentrantReadWriteLock. 一个读写锁管理一对相关的锁。 一个负责读操作，另外一个负责写操作。
 *     读操作在写锁没被使用时可同时由多个进程使用，而写锁使用时不允许读 (阻塞)。 此锁是可重入的。
 *     一个拥有写锁的线程可重入读锁，但是读锁却不能进入写锁。 这也意味着写锁可以降级成读锁，
 *     比如请求写锁 —>读锁 —->释放写锁。 从读锁升级成写锁是不成的。
 *     主要由两个类实现：
 *     InterProcessReadWriteLock
 *     InterProcessLock
 * </p>
 *
 * @author Damon
 * @since 2015/12/17 14:13
 */
public class InterProcessReadWriteExample {
    private static final int QTY = 5;
    private static final int REPETITIONS = QTY * 2;
    private static final String PATH = "/examples/locks";

    public static void main(String[] args) throws Exception {
        final FakeLimitedResource resource = new FakeLimitedResource();
        ExecutorService service = Executors.newFixedThreadPool(QTY);
        try {
            for (int i = 0; i < QTY; ++i) {
                final int index = i;
                Callable<Void> task = new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        CuratorFramework client = CuratorFrameworkFactory.newClient(Constants.HOST_AND_PORT,
                                new ExponentialBackoffRetry(1000, 3));
                        try {
                            client.start();
                            final ExampleClientReadWriteLocks example = new ExampleClientReadWriteLocks(client,
                                    PATH, resource, "Client " + index);
                            for (int j = 0; j < REPETITIONS; ++j) {
                                example.doWork(10, TimeUnit.SECONDS);
                            }
                        } catch (Throwable e) {
                            e.printStackTrace();
                        } finally {
                            CloseableUtils.closeQuietly(client);
                        }
                        return null;
                    }
                };
                service.submit(task);
            }
            service.shutdown();
            service.awaitTermination(10, TimeUnit.MINUTES);
        } finally {
        }
    }
}
