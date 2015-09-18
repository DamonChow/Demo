package com.damon.redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

/**
 * 功能：redis cluster test
 *
 * Created by DamonChow Date: 2015/9/17 Time: 15:19
 */
public class RedisClusterTest {

	JedisCluster jc;
	@Before
	public void init(){
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		//Jedis Cluster will attempt to discover cluster nodes automatically
		jedisClusterNodes.add(new HostAndPort("172.16.4.177", 6801));
		jedisClusterNodes.add(new HostAndPort("172.16.4.177", 6802));
		jedisClusterNodes.add(new HostAndPort("172.16.4.177", 6803));
		jedisClusterNodes.add(new HostAndPort("172.16.4.177", 6804));
		jedisClusterNodes.add(new HostAndPort("172.16.4.177", 6805));
		jedisClusterNodes.add(new HostAndPort("172.16.4.177", 6806));
		jc = new JedisCluster(jedisClusterNodes);

	}

	@Test
	public void testSet() {
		jc.set("foo", "bar");
		String value = jc.get("foo");
		System.out.println("value=" + value);
		jc.close();
	}


}
