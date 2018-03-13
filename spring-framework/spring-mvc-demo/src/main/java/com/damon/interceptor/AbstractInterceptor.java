package com.damon.interceptor;

import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 功能：流控拦截器抽象类
 *
 * @author zhoujiwei@idvert.com
 * @since 2018/1/19 11:46
 */
public abstract class AbstractInterceptor extends HandlerInterceptorAdapter implements InitializingBean {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private static String LIMIT_LUA = null;

    private final static String LimitLuaFileName = "limit.lua";

    public static final long SUCCESS = 1L;

    @Autowired
//    private RedisService redisService;

    @Override
    public void afterPropertiesSet() {
        LIMIT_LUA = loadScriptString(LimitLuaFileName);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long limitTime = getLimitTime();
        Long limitCount = getLimitCount();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(limitTime);
        List<String> keys = getKeys(request);
        List<String> args = Lists.newArrayList(limitCount.toString(), String.valueOf(seconds));
//        long result = redisService.eval(LIMIT_LUA, keys, args);
        long result = 1;
        if (result != SUCCESS) {
            String uri = RequestUtil.getRequestURI(request);
            String limitType = getLimitType();
            log.error("当前{}|{}|请求|{}|被系统限流，{}级别{}秒限流{}。", limitType, keys, uri, limitType, seconds, limitCount);
            /*ResultInfo resultInfo = ResultInfoUtil.returnResultInfo(getErrorEnum(), null);
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

    /**
     * 获取限制级别
     *
     * @return
     */
    protected abstract String getLimitType();

    /**
     * 获取lua参数
     *
     * @return
     */
    protected abstract ArrayList<String> getKeys(HttpServletRequest request);

    /**
     * 获取限制数量
     *
     * @return
     */
    protected abstract Long getLimitCount();

    /**
     * 获取限制时间
     *
     * @return
     */
    protected abstract Long getLimitTime();

    /**
     * 加载Lua代码
     */
    private String loadScriptString(String fileName) {
        try (Reader reader = new InputStreamReader(IpLimiterInterceptor.class.getClassLoader().getResourceAsStream(fileName))) {
            return CharStreams.toString(reader);
        } catch (IOException e) {
            throw new IllegalArgumentException("【" + fileName + "】lua脚本加载失败:", e);
        }
    }
}
