package com.damon.interceptor;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Request工具
 *
 * @author huxiaoqing@advertchina.com
 * @since 2015-01-13
 */
public final class RequestUtil {

	private static final String UNKNOWN = "unknown";

	/**
	 * Header不区分大小写, 此处强制规定所有小写
	 */
	private static final String[] HEADERS_IPS = new String[] { "x-forwarded-for", "x-real-ip", "proxy-client-ip",
			"wl-proxy-client-ip" };

	private static final String[] HEADERS_USER_AGENT = new String[] { "user-agent", "device-stock-ua",
			"x-device-user-agent", "x-device-user-agent", "x-operamini-phone-ua" };

	private RequestUtil() {
	}

	/**
	 * 取得当前用户IP
	 */
	public static String getIP(HttpServletRequest request) {
		return getIP(request, false);
	}

	/**
	 * 取得当前用户IP
	 */
	public static String getIP(HttpServletRequest request, boolean randomGen) {
		for (String current : HEADERS_IPS) {
			String ips = request.getHeader(current);
			if (ips != null && isValidIp(ips)) {
				String[] strs = StringUtils.split(ips, ',');
				if (ArrayUtils.isNotEmpty(strs)) {
					return StringUtils.trim(strs[0]);
				}
			}
		}
		return request.getRemoteAddr();

	}

	/**
	 * 获取User Agent
	 */
	public static String getUserAgent(HttpServletRequest request) {
		for (String current : HEADERS_USER_AGENT) {
			String ua = request.getHeader(current);
			if (ua != null) {
				return ua;
			}
		}
		return null;
	}

	/**
	 * 是否是有效的ip地址
	 */
	private static boolean isValidIp(String ip) {
		if (StringUtils.isEmpty(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
			return false;
		}
		return true;

	}

	/**
	 * 获取地址栏地址
	 * 
	 * @Title getHost
	 * @return 方法注释
	 */
	public static String getRequestURL(HttpServletRequest request) {
		StringBuffer tmp = request.getRequestURL();
		String contextPath = request.getContextPath();
		String uri = request.getRequestURI();
		String url = tmp.substring(0, (tmp.indexOf(uri))) + contextPath + "/";
		return url;
	}

	/**
	 * 获取URI
	 */
	public static String getRequestURI(HttpServletRequest request) {
		return request.getRequestURI();
	}


}
