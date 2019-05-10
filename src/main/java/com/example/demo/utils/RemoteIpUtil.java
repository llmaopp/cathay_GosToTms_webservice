package com.example.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoteIpUtil {
	private static Logger log = LoggerFactory.getLogger(RemoteIpUtil.class);
	private static final ThreadLocal threadLocal = new ThreadLocal();

	public static void setRemoteIp(String ip) {

		if (!StringUtil.empty(ip)) {
			threadLocal.set(ip);
		} else {
			// 如果从负载均衡获取的IP为空，传空字符串
			threadLocal.set("");
		}
	}

	public static String getRemoteIp() {
		String ip = (String) threadLocal.get();
		// threadLocal.remove();
		return StringUtil.empty(ip)?"127.0.0.1":ip;
	}

	public static void removeRemoteIp() {
		threadLocal.remove();
	}

	public static String getRemoteIpOnly() {
		String ip = (String) threadLocal.get();
		return ip;
	}
}
