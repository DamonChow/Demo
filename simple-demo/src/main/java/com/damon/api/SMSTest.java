package com.damon.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 功能：
 *
 * Created by Damon Date: 2015/9/18 Time: 10:43
 */
public class SMSTest {

	public static void main(String[] args) {
		String httpUrl = "http://apis.baidu.com/chonry/chonrysms/chonryapi";
		String httpArg = "content=%E5%88%9B%E7%91%9E%E6%B5%8B%E8%AF%95%E7%9F%AD%E4%BF%A1%E9%AA%8C%E8%AF%81%E7%A0%812015&mobile=13691886209";
		String jsonResult = request(httpUrl, httpArg);
		System.out.println(jsonResult);
	}

	/**
	 * @param httpUrl  :请求接口
	 * @param httpArg :参数
	 *
	 * @return 返回结果
	 */
	public static String request(String httpUrl, String httpArg) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey",  "81f02753c7b03507278bd109bdce0baa");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}