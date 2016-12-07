package com.damon.redis;

import com.google.common.collect.Lists;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * 功能：redis cluster test
 *
 * Created by DamonChow Date: 2015/9/17 Time: 15:19
 */
public class RedisClusterTest {

	JedisCluster jc;

	private String SETNX_SCRIPT =
			"local key = KEYS[1] " +
					"local ttl = ARGV[2] " +
					"local content = ARGV[1] " +
					"local lockSet = redis.call(\'setnx\', key, content) " +
					"if lockSet == 1 " +
					"then " +
					"   redis.call(\'expire\', key, ttl) " +
					"end " +
					"return lockSet ";

	@Before
	public void init(){
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		jedisClusterNodes.add(new HostAndPort("10.1.6.67", 6379));
		jedisClusterNodes.add(new HostAndPort("10.1.6.68", 6379));
		jedisClusterNodes.add(new HostAndPort("10.1.6.69", 6379));
		jc = new JedisCluster(jedisClusterNodes);
	}

	@Test
	public void testDel() {
		Long result = jc.del("AAA");
		System.out.println("result=" + result);
	}

	@Test
	public void test() {
		try {
			ExecutorService executorService = Executors.newFixedThreadPool(4);
			List<Callable<Long>> param = new ArrayList();

			for (int i=0;i<4;i++) {
                param.add(() -> (Long)jc.eval(SETNX_SCRIPT, Lists.newArrayList("AAA"), Lists.newArrayList("a", "10800")));
            }
			List<Future<Long>> futures = executorService.invokeAll(param);
			futures.stream().forEach(longFuture -> {
				try {
					System.out.println(longFuture.get());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//jc.eval(SETNX_SCRIPT, Lists.newArrayList("AAA"), Lists.newArrayList("a", "10800"));
	}

}