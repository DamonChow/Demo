package com.damon.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 功能：鉴权
 *
 * @author Damon
 * @since 2017/11/6 15:33
 */
@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获得请求路径的uri
        String uri = request.getRequestURI();
        logger.info("-----------{}", uri);
        return true;
        /*
        // 进入登录页面，判断session中是否有key，有的话重定向到首页，否则进入登录界面
        if(uri.contains("login")) {
            if(request.getSession().getAttribute("user") != null) {
                response.sendRedirect(request.getContextPath());//默认跟路径为首页
            } else {
                return true;//继续登陆请求
            }
        }

        // 其他情况判断session中是否有key，有的话继续用户的操作
        if(request.getSession().getAttribute("user") != null) {
            return true;
        }

        // 最后的情况就是进入登录页面
        response.sendRedirect(request.getContextPath() + "/login");
        return false;*/
    }
}
