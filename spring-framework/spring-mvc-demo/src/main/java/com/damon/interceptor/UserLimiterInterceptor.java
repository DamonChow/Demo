package com.damon.interceptor;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * 功能：用户限流拦截器  每秒100个请求
 *
 * @author Damon
 * @since 2017/11/6 15:33
 */
@Component
public class UserLimiterInterceptor extends AbstractInterceptor {

    @Override
    protected String getLimitType() {
        return "USER";
    }

    @Override
    protected ArrayList<String> getKeys(HttpServletRequest request) {
        //TODO 用户id
        Integer memberId = 0;
        Assert.notNull(memberId, "Please login again!");
        return Lists.newArrayList(memberId.toString());
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