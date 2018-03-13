package com.damon.interceptor;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * 功能：IP限流拦截器  每秒100个请求
 *
 * @author zhoujiwei@idvert.com
 * @since 2017/11/6 15:33
 */
@Component
public class IpLimiterInterceptor extends AbstractInterceptor {

    @Override
    protected String getLimitType() {
        return "IP";
    }

    @Override
    protected ArrayList<String> getKeys(HttpServletRequest request) {
        String ip = RequestUtil.getIP(request);
        return Lists.newArrayList(ip);
    }

    @Override
    protected Long getLimitCount() {
        return 100L;
    }

    @Override
    protected Long getLimitTime() {
        return 60L;
    }

}