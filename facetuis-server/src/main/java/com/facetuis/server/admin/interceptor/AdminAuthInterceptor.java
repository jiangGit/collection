package com.facetuis.server.admin.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.facetuis.server.model.admin.AdminUsers;
import com.facetuis.server.service.admin.AdminUsersService;
import com.facetuis.server.service.basic.BaseResult;
import com.facetuis.server.utils.LoginUtils;
import com.facetuis.server.utils.SysFinalValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

@Component
public class AdminAuthInterceptor implements HandlerInterceptor {


    private static final Logger logger = Logger.getLogger("后台授权校验");

    @Value("${sys.accesstoken.timeout}")
    private String tokenTimeOut;

    @Autowired
    private AdminUsersService adminUsersService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!HandlerMethod.class.isAssignableFrom(handler.getClass())){
            return true;
        }
        String accessToken = request.getHeader(SysFinalValue.ADMIN_ACCESS_TOKEN);
        boolean isNeedLogin = LoginUtils.isNeedLogin((HandlerMethod) handler);
        // 判断是否访问的接口是否需要登录
        if(isNeedLogin){
            boolean loginResult = false;
            if(!StringUtils.isEmpty(accessToken)){
                AdminUsers adminUsers = adminUsersService.findByAccessToken(accessToken);
                if(adminUsers == null){
                    BaseResult result = new BaseResult();
                    result.setCode(100);
                    result.setMessage("AccessToken验证失败");
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().flush();
                    response.getWriter().print(JSONObject.toJSON(result));
                    return false;
                }
                request.setAttribute(SysFinalValue.ADMIN_USER_PARAMTER_KEY, adminUsers);
            }
            return loginResult;
        }else{
            return true;
        }
    }

}
