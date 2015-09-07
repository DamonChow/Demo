package com.damon.test;

import java.lang.reflect.Array;

/**
 * 功能：
 *
 * Created by ZhouJW on 2015/1/15 15:51.
 */
public class ArrayTest {

    public static void main(String[] args) {
        /*Object o = Array.newInstance(String.class, 4);
        String[] a = new String[]{};
        System.out.println(o.getClass());
        System.out.println(a.getClass());
        System.out.println(String[].class);

		Integer integer = 1;
		System.out.println(integer.getClass());
        System.out.println(int.class);*/


		//CPU并不是逐个字节地访问内存。相反，它以（典型的）64字节的块为单位取内存，
		// 称作缓存行（cache lines）
		int[] arr = new int[64 * 1024 * 1024];
		long start = System.currentTimeMillis();
		// 循环1
		for (int i = 0; i < arr.length; i++)
			arr[i] *= 3;
		long end = System.currentTimeMillis();
		System.out.println("循环1开始：" + start + "ms");
		System.out.println("循环1结束：" + end + "ms");
		System.out.println("循环1时间：" + (end-start) + "ms");

		// 循环2
		start = System.currentTimeMillis();
		for (int i = 0; i < arr.length; i += 128)
			arr[i] *= 3;
		end = System.currentTimeMillis();
		System.out.println("循环2开始：" + start + "ms");
		System.out.println("循环2结束：" + end + "ms");
		System.out.println("循环2时间：" + (end-start) + "ms");
    }
}
