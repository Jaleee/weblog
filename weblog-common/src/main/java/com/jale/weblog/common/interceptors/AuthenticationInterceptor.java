package com.jale.weblog.common.interceptors;

import com.jale.weblog.common.annotations.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        IgnoreAuth ignoreAuth = null;
        if (handler instanceof HandlerMethod) {
            ignoreAuth = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuth.class);
        }

        if (ignoreAuth != null) {
            LOGGER.info("跳过认证");
            return true;
        }

        try {
            LOGGER.info("执行认证流程");

            return true;
        } catch (Exception e) {
            return false;
        }
    }



}
