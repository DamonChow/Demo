package com.damon;

import feign.Param;
import feign.RequestLine;

import java.util.List;

/**
 * 功能：
 *
 * @author zhoujiwei@idvert.com
 * @since 2017/10/23 15:31
 */
public interface BingApi {

    /**
     * https://api.bing.com/osjson.aspx?query=baidu.com+c&mkt=en-us
     * @param query
     * @param mkt
     * @return
     */
    @RequestLine("GET /osjson.aspx")
    String osjson(@Param("query") String query, @Param("mkt") String mkt);
}
