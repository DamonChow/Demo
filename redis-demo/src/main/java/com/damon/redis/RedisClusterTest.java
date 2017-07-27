package com.damon.redis;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 功能：redis cluster test
 *
 * @author damon
 * @since 2015/9/17 Time: 15:19
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
		jc = RedisUtils.getRedisCluster();
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

	@Test
	public void testIncr() {
        jc.incr("atest");
    }

}