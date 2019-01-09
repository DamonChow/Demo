package com.damon.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class SimpleContext {


    private static String[] IP_HEADS = new String[]{"x-forwarded-for", "proxy-client-ip",
            "wl-proxy-client-ip",
            "http_client_ip", "http_x_forwarded_for",};

    public static String getRemoteAddr() {
        for (String header : IP_HEADS) {
            String ip = getRequest().getHeader(header);
            if (ip != null && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip)) {
                if (ip.indexOf(",") > -1) {
                    ip = ip.substring(0, ip.indexOf(","));
                }
                return ip;
            }
        }
        return getRequest().getRemoteAddr();
    }


    public static HttpServletRequest getRequest() {
        RequestAttributes attr = RequestContextHolder.getRequestAttributes();
        if (attr instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) attr).getRequest();
        } else {
            if (attr != null) {
                log.error("获取Request请求失败：" + attr.getClass().getName());
            }
        }
        return null;
    }


}
