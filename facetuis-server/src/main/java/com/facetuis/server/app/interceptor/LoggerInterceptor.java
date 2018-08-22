package com.facetuis.server.app.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;
@Component
public class LoggerInterceptor implements HandlerInterceptor {

    private static final Logger logger = Logger.getLogger("请求记录");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("request URL :: " + request.getRequestURI() + "?" + (request.getQueryString() == null ? "" : request.getQueryString() )+ " | " + request.getMethod());
        return true;
    }
}
