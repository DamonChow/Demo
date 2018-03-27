package com.damon.interceptor;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 功能：限流拦截器  每秒3000个请求
 *
 * @author Damon
 * @since 2017/11/6 15:33
 */
@Component
public class WebLimiterInterceptor extends HandlerInterceptorAdapter {
	
    private static final Logger log = LoggerFactory.getLogger(WebLimiterInterceptor.class);

    private final static int INITIAL_VALUE = 0;

    private LoadingCache<Long, AtomicLong> counter = CacheBuilder.newBuilder()
            .expireAfterWrite(3000L, TimeUnit.MILLISECONDS)
            .build(new CacheLoader<Long, AtomicLong>() {
                @Override
                public AtomicLong load(Long arg0) {
                    return new AtomicLong(INITIAL_VALUE);
                }
            });

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long limitTime = 60L;
        Long limitCount = 3000L;
        long curr = System.currentTimeMillis() / limitTime;
        if (counter.get(curr).incrementAndGet() > limitCount) {
            long seconds = TimeUnit.MILLISECONDS.toSeconds(limitTime);
            String ip = RequestUtil.getIP(request);
            String uri = RequestUtil.getRequestURI(request);

            log.error("当前ip|{}|请求|{}|被系统限流，系统级别{}秒限流{}。", ip, uri, seconds, limitCount);
           /* ResultInfo resultInfo = ResultInfoUtil.returnResultInfo(ResultInfoDescEnum.REQUEST_FREQUENTLY, null);
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Origin", PropertiesUtil.getAccessControlAllowOrigin());
            response.getWriter().println(UtilJson.writeValueAsString(resultInfo));
            response.getWriter().flush();
            response.getWriter().close();*/
            return false;
        }

        return true;
    }

}