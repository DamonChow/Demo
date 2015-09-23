package com.damon.memcached;

import net.spy.memcached.MemcachedClient;

import java.net.InetSocketAddress;
import java.util.concurrent.CountDownLatch;

public class SpyPerformanceTest {

	static class TestWriteRunnable implements Runnable {

		private MemcachedClient mc;
		private CountDownLatch cd;
		int repeat;
		int start;

		public TestWriteRunnable(MemcachedClient mc, int start,
				CountDownLatch cdl, int repeat) {
			super();
			this.mc = mc;
			this.start = start;
			this.cd = cdl;
			this.repeat = repeat;

		}

		public void run() {
			try {

				for (int i = 0; i < repeat; i++) {
					String key = String.valueOf(start + i);
					if (!mc.set(key, 0, key).get()) {
						System.err.println("set error");
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				cd.countDown();
			}
		}

	}

	static class TestReadRunnable implements Runnable {

		private MemcachedClient mc;
		private CountDownLatch cd;
		int repeat;
		int start;

		public TestReadRunnable(MemcachedClient mc, int start,
				CountDownLatch cdl, int repeat) {
			super();
			this.mc = mc;
			this.start = start;
			this.cd = cdl;
			this.repeat = repeat;

		}

		public void run() {
			try {
				for (int i = 0; i < repeat; i++) {

					String key = String.valueOf(start + i);
					String result = (String) mc.get(key);
					if (!key.equals(result)) {
						System.out.println(key + " " + result);
						System.err.println("get error");
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				cd.countDown();
			}
		}

	}

	static class TestDeleteRunnable implements Runnable {

		private MemcachedClient mc;
		private CountDownLatch cd;
		int repeat;
		int start;

		public TestDeleteRunnable(MemcachedClient mc, int start,
				CountDownLatch cdl, int repeat) {
			super();
			this.mc = mc;
			this.start = start;
			this.cd = cdl;
			this.repeat = repeat;

		}

		public void run() {
			try {
				for (int i = 0; i < repeat; i++) {
					String key = String.valueOf(start + i);
					if (!mc.delete(key).get())
						System.err.println("delete error");
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				cd.countDown();
			}
		}

	}

	// thread num=10, repeat=10000,size=2, all=200000 ,velocity=1057 , using
	// time:189187
	static public void main(String[] args) {
		try {
			String ip = "127.0.0.1";

			int port = 11211;

			int size = Runtime.getRuntime().availableProcessors();

			int thread = 100;

			int repeat = 10000;

			MemcachedClient mc = new MemcachedClient(new InetSocketAddress(ip,
					port));

			CountDownLatch cdl = new CountDownLatch(thread);
			// ����д
			long t = System.currentTimeMillis();
			for (int i = 0; i < thread; i++) {
				new Thread(new TestWriteRunnable(mc, i * 10000,
						cdl, repeat)).start();
			}
			try {
				cdl.await();
			} catch (InterruptedException e) {

			}
			long all = thread * repeat;
			long usingtime = (System.currentTimeMillis() - t);

			System.out
					.println(String
							.format(
									"test write,thread num=%d, repeat=%d,size=%d, all=%d ,velocity=%d , using time:%d",
									thread, repeat, size, all, 1000 * all
											/ usingtime, usingtime));

			// ���Զ�
			cdl = new CountDownLatch(thread);
			t = System.currentTimeMillis();
			for (int i = 0; i < thread; i++) {
				new Thread(new TestReadRunnable(mc, i * 10000,
						cdl, repeat)).start();
			}
			try {
				cdl.await();
			} catch (InterruptedException e) {

			}
			all = thread * repeat;
			usingtime = (System.currentTimeMillis() - t);
			System.out
					.println(String
							.format(
									"test read,thread num=%d, repeat=%d,size=%d, all=%d ,velocity=%d , using time:%d",
									thread, repeat, size, all, 1000 * all
											/ usingtime, usingtime));
			// ����ɾ��
			cdl = new CountDownLatch(thread);
			t = System.currentTimeMillis();
			for (int i = 0; i < thread; i++) {
				new Thread(new TestDeleteRunnable(mc,
						i * 10000, cdl, repeat)).start();
			}
			try {
				cdl.await();
			} catch (InterruptedException e) {

			}
			all = thread * repeat;
			usingtime = (System.currentTimeMillis() - t);
			System.out
					.println(String
							.format(
									"test delete,thread num=%d, repeat=%d,size=%d, all=%d ,velocity=%d , using time:%d",
									thread, repeat, size, all, 1000 * all
											/ usingtime, usingtime));

			mc.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
