package com.damon.zookeeper.curator.lock.shared.semaphore;

import com.damon.zookeeper.Constants;
import com.damon.zookeeper.curator.lock.shared.FakeLimitedResource;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreV2;
import org.apache.curator.framework.recipes.locks.Lease;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * 功能：多锁对象 Multi Shared Lock
 *
 * <p>
 *     一个计数的信号量类似JDK的Semaphore。 JDK中Semaphore维护的一组许可(permits)，而Curator中称之为租约(Lease)。
 *     有两种方式可以决定semaphore的最大租约数。第一种方式是有用户给定的path决定。第二种方式使用SharedCountReader类。
 *     如果不使用SharedCountReader, 没有内部代码检查进程是否假定有10个租约而进程B假定有20个租约。
 *     所以所有的实例必须使用相同的numberOfLeases值.
 *     这次调用acquire会返回一个租约对象。 客户端必须在finally中close这些租约对象，否则这些租约会丢失掉。
 *     但是， 但是，如果客户端session由于某种原因比如crash丢掉，
 *     那么这些客户端持有的租约会自动close， 这样其它客户端可以继续使用这些租约。
 * </p>
 * @author Damon
 * @since 2015/12/17 15:02
 */
public class InterProcessSemaphoreExample {

    private static final int MAX_LEASE = 10;
    private static final String PATH = "/examples/locks";

    /*租约还可以通过下面的方式返还：

    public void returnAll(Collection<Lease> leases)
    public void returnLease(Lease lease)*/

    /*注意一次你可以请求多个租约，如果Semaphore当前的租约不够，则请求线程会被阻塞。 同时还提供了超时的重载方法。

    public Lease acquire()
    public Collection<Lease> acquire(int qty)
    public Lease acquire(long time, TimeUnit unit)
    public Collection<Lease> acquire(int qty, long time, TimeUnit unit)*/

    public static void main(String[] args) throws Exception {
        FakeLimitedResource resource = new FakeLimitedResource();
        CuratorFramework client = CuratorFrameworkFactory.newClient(Constants.HOST_AND_PORT,
                new ExponentialBackoffRetry(1000, 3));
        client.start();

        InterProcessSemaphoreV2 semaphore = new InterProcessSemaphoreV2(client, PATH, MAX_LEASE);
        Collection<Lease> leases = semaphore.acquire(5);
        System.out.println("get " + leases.size() + " leases");
        Lease lease = semaphore.acquire();
        System.out.println("get another lease");

        resource.use();

        Collection<Lease> leases2 = semaphore.acquire(5, 10, TimeUnit.SECONDS);
        System.out.println("Should timeout and acquire return " + leases2);

        System.out.println("return one lease");
        semaphore.returnLease(lease);
        System.out.println("return another 5 leases");
        semaphore.returnAll(leases);

        client.close();
    }
}
