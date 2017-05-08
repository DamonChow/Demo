package com.damon.example;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 功能：
 *
 * Created by Domon Chow on 2015/1/15 15:51.
 */
public class ArrayTest {

    public static void main(String[] args) {
        Long[] longs = {11l,22l,33l};
        System.out.println(Arrays.asList(longs));
        System.out.println("-------------------------------");
        //校验string的split方法，当没有可分组时，得到的数组不为空，length=1
        String ss = "sf";
        String[] split = ss.split("#");
        System.out.println(split + "---" + split.length);

        String s = "@123#2#22";
        int lastIndex = s.lastIndexOf("#");
        System.out.println("the string is " + s);
        System.out.println("the last # is " + lastIndex);
        System.out.println("last string is " + s.substring(lastIndex+1));
        System.out.println("the before except @ is " + s.substring(1, lastIndex));


        /*Object o = Array.newInstance(String.class, 4);
        String[] a = new String[]{};
        System.out.println(o.getClass());
        System.out.println(a.getClass());
        System.out.println(String[].class);

		Integer integer = 1;
		System.out.println(integer.getClass());
        System.out.println(int.class);*/


		//CPU并不是逐个字节地访问内存。相反，它以（典型的）64字节的块为单位取内存，
        //http://blog.jobbole.com/89759/
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

    @Test
    public void test() {
        List<String> list = new ArrayList<>();
        list.add("Java");
        list.add("python");
        list.add("c++");
        list.add("c");
        list.add("lisp");

        String[] arr = new String[0];
        list.stream().filter(p -> p.length() > 3).toArray(value -> arr);
        System.out.println(Arrays.toString(arr));
    }
}
