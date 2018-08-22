package com.facetuis.server.app.interceptor;

import com.facetuis.server.model.user.User;
import com.facetuis.server.model.user.UserLevel;
import com.facetuis.server.service.user.UserService;
import com.facetuis.server.utils.SysFinalValue;
import com.facetuis.server.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserLevelInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object attribute = request.getAttribute(SysFinalValue.USER_PARAMTER_KEY);
        if(attribute != null){
            User user = (User)attribute;
            if(user.getLevel() == UserLevel.LEVEL2) {
                if (TimeUtils.compare(user.getExpireTime()) > 0) {
                    userService.lowerLevel(user);
                }
            }
        }
        return true;
    }
}
