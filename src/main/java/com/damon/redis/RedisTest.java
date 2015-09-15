package com.damon.redis;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.*;
import redis.clients.util.ShardInfo;

import java.util.*;

/**
 * 功能：Jedis Test
 *
 * Created by Domon Chow Date: 2015/8/25 Time: 16:30
 */
public class RedisTest {

	JedisPool pool;

	Jedis jedis;

	ShardedJedisPool shardedJedisPool;

	ShardedJedis shardedJedis;


	@Before
	public void init() {
		pool = new JedisPool(new JedisPoolConfig(), "172.16.4.177", 6801);
		//pool = new JedisPool(new JedisPoolConfig(), "172.16.4.171", 6379);
		//pool = new JedisPool(new JedisPoolConfig(), "172.31.1.22", 6379);
		jedis = pool.getResource();
		//jedis = new Jedis("localhost");

		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo("172.16.4.177", 6801));
		shards.add(new JedisShardInfo("172.16.4.177", 6802));
		shardedJedisPool = new ShardedJedisPool(new JedisPoolConfig(), shards);

		shardedJedis = shardedJedisPool.getResource();
	}

	@Test
	public void testShardedJedis() {
		try {
			shardedJedis.set("a", "aaa");
			shardedJedis.set("b", "bbb");
			shardedJedis.set("c", "ccc");
			shardedJedis.set("d", "ddd");
			shardedJedis.set("e", "eee");

			//根据key获取jedis
			JedisShardInfo si = shardedJedis.getShardInfo("a");
			System.out.println(si.getHost());
		} finally {
			jedis.close();
			pool.destroy();
			shardedJedis.close();
			shardedJedisPool.destroy();
		}
	}

	@Test
	public void testPing() {
		try {
			System.out.println("server is running: " + jedis.ping());
		} finally {
			jedis.close();
			pool.destroy();
			shardedJedis.close();
			shardedJedisPool.destroy();
		}
	}

	@Test
	public void testPipelining() {
		try {
			Pipeline p = jedis.pipelined();
			p.set("fool", "bar");
			p.zadd("foo", 1, "barowitch");
			p.zadd("foo", 0, "barinsky");
			p.zadd("foo", 0, "barikoviev");
			Response<String> pipeString = p.get("fool");
			Response<Set<String>> sose = p.zrange("foo", 0, -1);
			p.sync();
			int soseSize = sose.get().size();
			Set<String> setBack = sose.get();
			System.out.println("pipeString = " + pipeString + ",result = " + pipeString.get());
			System.out.println("soseSize = " + soseSize);
			System.out.println("setBack = " + setBack);
		} finally {
			jedis.close();
			pool.destroy();
			shardedJedis.close();
			shardedJedisPool.destroy();
		}
	}

	@Test
	public void testKeys() {
		try {
			System.out.println("All key is : " + jedis.keys("*"));
		} finally {
			jedis.close();
			pool.destroy();
			shardedJedis.close();
			shardedJedisPool.destroy();
		}
	}

	@Test
	public void testMonitor() {
		/*jedis.monitor(new JedisMonitor() {
			public void onCommand(String command) {
				System.out.println("command is " + command);
			}
		});*/

		try {
			new Thread(new Runnable() {
				public void run() {
					for (int i = 0; i < 100; i++) {
						jedis.incr("test1");
						/*try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
						}*/
					}
					jedis.disconnect();
				}
			}).start();
		} finally {
			jedis.close();
			pool.destroy();
			shardedJedis.close();
			shardedJedisPool.destroy();
		}
	}

	@Test
	public void testForEach() {
		try {
			for (int i = 0; i < 100001; i++) {
				jedis.set("test", i + "");
			}
			System.out.println(jedis.get("test"));
		} finally {
			jedis.close();
			pool.destroy();
			shardedJedis.close();
			shardedJedisPool.destroy();
		}
	}

	@Test
	public void testGet() {
		//jedis.set("test", "test22");
		// 测试关闭redis后，在开启，test是否持久化
		try {
			System.out.println(jedis.get("test"));
		} finally {
			jedis.close();
			pool.destroy();
			shardedJedis.close();
			shardedJedisPool.destroy();
		}
	}

	/**
	 * Redis存储初级的字符串 CRUD
	 */
	@Test
	public void testBasicString() {
		try {
			//-----添加数据----------
			jedis.set("name", "da");//向key-->name中放入了value-->da
			System.out.println(jedis.get("name"));//执行结果：da
			//-----修改数据-----------
			//1、在原来基础上修改
			jedis.append("name", "mon");   //很直观，类似map 将mon append到已经有的value之后
			System.out.println(jedis.get("name"));//执行结果:damon
			//2、直接覆盖原来的数据
			jedis.set("name", "test");
			System.out.println(jedis.get("name"));//执行结果：test
			//删除key对应的记录
			jedis.del("name");
			System.out.println(jedis.get("name"));//执行结果：null

			//mset相当于 jedis.set("name","minxr");jedis.set("jarorwar","闵晓荣");
			jedis.mset("name", "test", "name2", "test2");
			System.out.println(jedis.mget("name", "name2"));//[test, test2]
		} finally {
			jedis.close();
			pool.destroy();
			shardedJedis.close();
			shardedJedisPool.destroy();
		}
	}

	/**
	 * jedis操作Map
	 */
	@Test
	public void testMap() {
		try {
			Map<String, String> user = new HashMap<String, String>();
			user.put("name", "damon");
			user.put("number", "123456");
			user.put("sex", "M");
			jedis.hmset("map", user);

			//第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数
			List<String> rsmap = jedis.hmget("map", "name");//取出map中的name，执行结果:[damon]
			System.out.println(rsmap);
			rsmap = jedis.hmget("map", "name", "sex");//[damon, M]
			System.out.println(rsmap);

			//删除map中的key
			Long hdel = jedis.hdel("map", "sex");
			System.out.println(hdel);//1
			System.out.println(jedis.hmget("map", "sex")); //因为删除了，所以返回的是[null]
			System.out.println(jedis.hlen("map")); //返回key为map的键中存放的值的个数2
			System.out.println(jedis.exists("map"));//是否存在key为map的记录 返回true
			System.out.println(jedis.hkeys("map"));//返回map对象中的所有key  [number, name]
			System.out.println(jedis.hvals("map"));//返回map对象中的所有value  [damon, 123456]
			Iterator<String> iter = jedis.hkeys("map").iterator();
			while (iter.hasNext()) {
				String key = iter.next();
				System.out.println(key + ":" + jedis.hmget("map", key));
			}
		} finally {
			jedis.close();
			pool.destroy();
			shardedJedis.close();
			shardedJedisPool.destroy();
		}
	}

	/**
	 * jedis操作List
	 */
	@Test
	public void testList() {
		try {
			//开始前，先移除所有的内容
			jedis.del("list");
			System.out.println(jedis.lrange("list", 0, -1));
			//先向key list中存放数据
			jedis.lpush("list", "spring");
			jedis.lpush("list", "struts");
			jedis.lpush("list", "hibernate");
			jedis.lpush("list", "test");
			jedis.lpush("list", "aaa");
			//再取出所有数据jedis.lrange是按范围取出，
			// 第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
			//[aaa, test, hibernate, struts, spring]  可以看到结果是排序的
			System.out.println(jedis.lrange("list", 0, -1));
		} finally {
			jedis.close();
			pool.destroy();
			shardedJedis.close();
			shardedJedisPool.destroy();
		}
	}

	/**
	 * jedis操作Set
	 */
	@Test
	public void testSet() {
		try {
			//添加
			jedis.sadd("set", "123");
			jedis.sadd("set", "234");
			jedis.sadd("set", "345");
			jedis.sadd("set", "456");
			//移除456
			jedis.srem("set", "456");
			System.out.println(jedis.smembers("set"));//获取所有加入的value
			System.out.println(jedis.sismember("set", "123"));//判断 minxr 是否是set集合的元素
			System.out.println(jedis.srandmember("set"));
			System.out.println(jedis.scard("set"));//返回集合的元素个数
		} finally {
			jedis.close();
			pool.destroy();
			shardedJedis.close();
			shardedJedisPool.destroy();
		}
	}

	@Test
	public void test() throws InterruptedException {
		try {
			//keys中传入的可以用通配符
			System.out.println(jedis.keys("*")); //返回当前库中所有的key  [list, user, name2, set, name]
			System.out.println(jedis.keys("*name"));//返回以name结尾的   [name]
			System.out.println(jedis.del("sanmdde"));//删除key为sanmdde的对象  删除成功返回1 删除失败（或者不存在）返回 0
			System.out.println(jedis.ttl("sname"));//返回给定key的有效时间，如果是-1则表示永远有效
			jedis.setex("timekey", 10, "min");//通过此方法，可以指定key的存活（有效时间） 时间为秒
			Thread.sleep(5000);//睡眠5秒后，剩余时间将为<=5
			System.out.println(jedis.ttl("timekey"));   //输出结果为5
			jedis.setex("timekey", 1, "min");        //设为1后，下面再看剩余时间就是1了
			System.out.println(jedis.ttl("timekey"));  //输出结果为1
			System.out.println(jedis.exists("key"));//检查key是否存在System.out.println(jedis.rename("timekey","time"));
			System.out.println(jedis.get("timekey"));//因为移除，返回为null
			System.out.println(jedis.get("time")); //因为将timekey 重命名为time 所以可以取得值 min
			//jedis 排序
			//注意，此处的rpush和lpush是List的操作。是一个双向链表（但从表现来看的）
			jedis.del("a");//先清除数据，再加入数据进行测试
			jedis.rpush("a", "1");
			jedis.lpush("a", "6");
			jedis.lpush("a", "3");
			jedis.lpush("a", "9");
			System.out.println(jedis.lrange("a", 0, -1));// [9, 3, 6, 1]
			System.out.println(jedis.sort("a")); //[1, 3, 6, 9]  //输入排序后结果
			System.out.println(jedis.lrange("a", 0, -1));
		} finally {
			jedis.close();
			pool.destroy();
			shardedJedis.close();
			shardedJedisPool.destroy();
		}
	}

}