package com.damon.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;


@Slf4j
@AllArgsConstructor
@Order(1)
public class AccessLogFilter extends OncePerRequestFilter {

    private static final String DEFAULT_SKIP_PATTERN =
            "/webjars/springfox-swagger-ui.*|/v2/api-docs.*|/api-docs.*|/actuator.*|/swagger.*|.*\\.png|.*\\.css|.*\\.js|.*\\.html|/favicon.ico|/hystrix.stream";

    private static final Pattern SKIP_PATTERNS = Pattern.compile(DEFAULT_SKIP_PATTERN);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        if (ignoreRequest(httpServletRequest)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            final boolean isFirstRequest = !isAsyncDispatch(httpServletRequest);
            final AccessLogger accessLogger = new AccessLogger();
            HttpServletRequest requestToUse = httpServletRequest;
            ContentCachingResponseWrapper responseToUse =
                    new ContentCachingResponseWrapper(httpServletResponse);

            StopWatch watch = new StopWatch();
            watch.start();
            if (isFirstRequest && !(httpServletRequest instanceof ContentCachingRequestWrapper)) {
                requestToUse = new ContentCachingRequestWrapper(httpServletRequest,8192);
            }
            accessLogger.appendCreateTime();
            try {
                filterChain.doFilter(requestToUse, responseToUse);
            } finally {
                if (isFirstRequest) {
                    accessLogger.appendRequestCommonMessage(requestToUse);
                    accessLogger.appendRequestDetailMessage(true, requestToUse);
                }

                watch.stop();
                if (!isAsyncStarted(requestToUse)) {
                    accessLogger.appendResponseCommonMessage(responseToUse, watch.getTotalTimeMillis());
                    if (true && !isBinaryContent(httpServletResponse)
                            && !isMultipart(httpServletResponse)) {
                        accessLogger.appendResponseDetailMessage(responseToUse);
                    }
                    accessLogger.appendResponseLast();
                }

                responseToUse.copyBodyToResponse();
                accessLogger.printLog();


            }

        }
    }

    private boolean ignoreRequest(HttpServletRequest request) {
        String path = request.getRequestURI();
        return SKIP_PATTERNS.matcher(path).matches();
    }

    private boolean isBinaryContent(final HttpServletResponse response) {
        return response.getContentType() != null && (response.getContentType()
                .startsWith("image") || response.getContentType().startsWith("video") || response
                .getContentType().startsWith("audio"));
    }

    private boolean isMultipart(final HttpServletResponse response) {
        return response.getContentType() != null && (response.getContentType()
                .startsWith("multipart/form-data") || response.getContentType()
                .startsWith("application/octet-stream"));
    }


}
