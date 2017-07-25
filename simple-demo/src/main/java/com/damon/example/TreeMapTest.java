package com.damon.example;

import java.util.Map;
import java.util.TreeMap;

/**
 * 功能：
 *
 * Created by damon Date: 2015/8/19 Time: 17:02
 */
public class TreeMapTest {
	public static void main(String[] args) {

		TreeMap<Teacher, Object> map = new TreeMap<Teacher, Object>();
		map.put(new Teacher("aaa"), "1");
		map.put(new Teacher("aaa12"), "2");
		map.put(new Teacher("aaa14"), "3");
		map.put(new Teacher("aaa10"), "4");

		for(Map.Entry<Teacher, Object> entry : map.entrySet()) {
			System.out.println("key::::" + entry.getKey() + ",Value:::"  + entry.getValue().toString());
		}

		Teacher teacher = map.ceilingKey(new Teacher("aaa15"));
		System.out.println("ceilingKey:::" + teacher);
	}

	static class Teacher implements Comparable<Teacher>{

		private String name;

		public Teacher(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "Teacher{" +
					"name='" + name + '\'' +
					'}';
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {

			return name;
		}

		@Override
		public int compareTo(Teacher o) {
			if (o == null || (this.name == null && o.getName() == null)) {
				return 1;
			} else if (this.name == null) {
				return -1;
			} else if (o.getName() == null) {
				return 1;
			}

			return this.name.compareTo(o.getName());
		}
	}
}
