package com.facetuis.server.utils;

import org.springframework.web.method.HandlerMethod;

public class LoginUtils {

    public static boolean isNeedLogin(HandlerMethod handler){
        NeedLogin needLogin =   handler.getMethod().getAnnotation(NeedLogin.class);
        if (needLogin == null) {
            needLogin = handler.getBeanType().getAnnotation(NeedLogin.class);
        }
        if (needLogin != null ) {
            return needLogin.needLogin();
        }
        return false;
    }
}
