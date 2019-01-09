package com.damon.config;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class AccessLogger {

    private static Logger logger = LoggerFactory.getLogger("AccessLogger_Damon");

    AccessLogEs.AccessLogEsBuilder builder = AccessLogEs.builder();

    void appendCreateTime() {
        builder.timeStamp(new Date());
    }

    void appendRequestCommonMessage(HttpServletRequest request) {
        builder.url(request.getRequestURI()).method(request.getMethod());

        Map<String, String[]> params = request.getParameterMap();
        if (params != null && !params.isEmpty()) {
            builder.inParam(getParams(params));
        }

        builder.clientIp(SimpleContext.getRemoteAddr());
//        builder.appVersion(SimpleContext.getAppVersion());
//        builder.clientType(SimpleContext.getClientType());
    }

    void appendRequestDetailMessage(boolean includeRequest, HttpServletRequest request) {
        StringBuilder msg = new StringBuilder();
        if (includeRequest && isNormalRequest(request)) {
            builder.inHeadParam(new ServletServerHttpRequest(request).getHeaders().toString());
            ContentCachingRequestWrapper wrapper = WebUtils
                    .getNativeRequest(request, ContentCachingRequestWrapper.class);
            if (wrapper != null) {
                byte[] buf = wrapper.getContentAsByteArray();
                if (buf.length > 0) {
                    int length = Math.min(buf.length, 8192);
                    String payload;
                    try {
                        payload = new String(buf, 0, length, wrapper.getCharacterEncoding());
                    } catch (UnsupportedEncodingException ex) {
                        payload = "[unknown]";
                    }
                    msg.append(";payload=").append(payload);
                    builder.inParam(payload);
                }
            }
        }

    }

    void appendResponseCommonMessage(ContentCachingResponseWrapper response, long cost) {
        builder.cost(cost);
        builder.status(response.getStatusCode());
        builder.size(response.getContentSize());
    }

    void appendResponseDetailMessage(ContentCachingResponseWrapper response) {
        String contentType = response.getContentType();
        builder.outHeadParam(new ServletServerHttpResponse(response).getHeaders().toString());
        Optional.ofNullable(contentType).filter(c -> c.startsWith("application/json")).ifPresent(c -> {
            byte[] buf = response.getContentAsByteArray();
            if (buf.length > 0) {
                int length = Math.min(buf.length, 8192);
                String payload = null;
                try {
                    payload = new String(buf, 0, length, response.getCharacterEncoding());
                    Map<String, Object> map = ObjectMapperUtil.str2obj(payload, Map.class);
                    Object obj = map.get("errorCode");
                    if (obj != null) {
                        builder.result((Integer) obj);
                    }
                } catch (Exception ex) {
                    log.info("[payload]: {}", payload);
                    builder.result(0);
                    payload = "[unknown]";
                }
                builder.outParam(payload);
            }
        });
    }

    void appendResponseLast() {
    }

    void printLog() {
        logger.info(ObjectMapperUtil.obj2string(builder.build()));
    }

    private boolean isNormalRequest(HttpServletRequest request) {
        return !isMultipart(request) && !isBinaryContent(request);
    }

    private boolean isMultipart(final HttpServletRequest request) {
        return request.getContentType() != null && request.getContentType()
                .startsWith("multipart/form-data");
    }

    private boolean isBinaryContent(final HttpServletRequest request) {
        if (request.getContentType() == null) {
            return false;
        }
        return request.getContentType().startsWith("image") || request.getContentType()
                .startsWith("video") || request.getContentType().startsWith("audio");
    }

    private String getParams(final Map<String, String[]> params) {
        List<String> parts = Lists.newArrayList();
        params.forEach((k, v) -> {
            String param = k + "=[" + Arrays.stream(v).map(String::valueOf)
                    .collect(Collectors.joining(",")) + "]";
            parts.add(param);
        });
        return parts.stream().collect(Collectors.joining(","));
    }


    /**
     * es 日志对象
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AccessLogEs {

        /**
         * 客户端ip
         */
        private String clientIp;

        /**
         * 客户端类型
         */
        private String clientType;

        /**
         * 服务端ip
         */
        private String serverIp;
        /**
         * 调用方法
         */
        private String method;

        /**
         * 调用时间
         */
        private Date timeStamp;

        /**
         * 耗时时间毫秒
         */
        private Long cost;

        /**
         * 入头参数
         */
        private String inHeadParam;

        /**
         * 出头参数
         */
        private String outHeadParam;

        /**
         * 入参
         */
        private String inParam;

        /**
         * 出参
         */
        private String outParam;

        /**
         * 接口编码
         */
        private Integer result;

        /**
         * app版本号
         */
        private String appVersion;

        /**
         * 用户id
         */
        private Integer userId;

        /**
         * 响应状态
         */
        private Integer status;

        /**
         * 路径
         */
        private String url;

        /**
         * 响应大小
         */
        private long size;

    }


}
