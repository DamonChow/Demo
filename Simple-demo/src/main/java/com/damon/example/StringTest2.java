package com.damon.example;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能：
 *
 * Created by Domon Chow Date: 2015/9/1 Time: 15:42
 */
public class StringTest2 {

	public static void main(String args[]) {
		String html = "<title>ABCD</title>gsdggas<title></title>jkll<title>005</title>";
		// 简单示例，相当于String html=getHtml(String urlString);
		List resultList = getContext(html);
		for (Iterator iterator = resultList.iterator(); iterator.hasNext();) {
			String context = (String) iterator.next();
			System.out.println(context);
		}
		System.out.println(resultList.size());
		//getSinaIP();
	}

    @Test
    public void testStringLength() {
        String s = "什么是我们";
        System.out.println(s.length());
    }

	public static List getContext(String html) {
		List resultList = new ArrayList();
		Pattern p = Pattern.compile("<title>([^</title>]*)");// 匹配<title>开头，</title>结尾的文档
		Matcher m = p.matcher(html);// 开始编译
		while (m.find()) {
			resultList.add(m.group(0));// 获取被匹配的部分
		}
		return resultList;
	}

	/**
	 * @param html 要解析的html文档内容
	 * @return 解析结果，可以多次匹配，每次匹配的结果按文档中出现的先后顺序添加进结果List
	 */
	public static List getContexts(String html) {
		List resultList = new ArrayList();
		// <[^<|^>]*>
		// >*<
		// >([^<]*)
		// <title>([^</title>]*)
		// new Array\\(([^\\);]*)
		Pattern p = Pattern.compile("new Array\\(([^\\);]*)");
		Matcher m = p.matcher(html);// 开始编译
		while (m.find()) {
			int gc = m.groupCount();
			String group = m.group(1);
			if (!"".equals(group)) {
				System.out.println(gc + group);
				resultList.add(group);
			}
		}
		return resultList;
	}

	// var Q = "http://counter.sina.com.cn/ip";
	public static String[] getSinaIP() {
		try {
			String urls = "http://counter.sina.com.cn/ip";
			URL url = new URL(urls);
			URLConnection urlconn = url.openConnection();
			HttpURLConnection conn = (HttpURLConnection) urlconn;
			int httpResult = conn.getResponseCode();
			StringBuffer ipbuff = new StringBuffer();
			if (httpResult == HttpURLConnection.HTTP_OK) {
				InputStream is = conn.getInputStream();
				InputStreamReader isr = new InputStreamReader(is, "GBK");
				BufferedReader in = new BufferedReader(isr);
				String strLine;
				while ((strLine = in.readLine()) != null) {
					ipbuff.append(strLine);
				}
				in.close();
				isr.close();
				is.close();
			}
			String html = ipbuff.toString();
			Pattern p = Pattern.compile("new Array\\(([^\\);]*)");
			Matcher m = p.matcher(html);// 开始编译
			while (m.find()) {
				// int gc = m.groupCount();
				String group = m.group(1);
				if (!"".equals(group)) {
					html = group.replace("\"", "");
					html = html.replace(" ", "");
					// System.out.println(gc + group);
				}
			}
			System.out.println(html);
			String[] ipInfo = html.split(",");
			return ipInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
