package com.facetuis.server.app.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.utils.SignUtils;
import com.facetuis.server.utils.SysFinalValue;
import com.facetuis.server.utils.URLUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 * 校验请求签名是否合法
 */
@Component
public class SignInterceptor implements HandlerInterceptor {

    private static final Logger logger = Logger.getLogger("校验签名");

    @Value("${sys.sign.enable}")
    private String isEnableSign;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        // 启用签名验证
        if(Boolean.parseBoolean(isEnableSign)){
            String d = request.getParameter("d");
            String sign = request.getParameter("sign");
            String c = request.getHeader(SysFinalValue.CLIENT_TYPE);
            String method = request.getMethod();
            String uri = request.getRequestURI();
            String clientKey = getClientKey(c);
            String accessToken = request.getHeader(SysFinalValue.ACCESS_TOKEN);
            if(StringUtils.isEmpty(d) || StringUtils.isEmpty(sign) || StringUtils.isEmpty(c) || StringUtils.isEmpty(clientKey)){
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().flush();
                response.getWriter().print(JSONObject.toJSON(new BaseResponse(400,"缺少关键请求参数")));
                return  false;
            }
            // 判断用户是否已登录
            Object user = request.getAttribute(SysFinalValue.USER_PARAMTER_KEY);
            String key2 = "";
            if(user != null){
                // 登录
                key2 = accessToken.substring(accessToken.length() - 6);
            }else{
                // 未登录
                key2 = method;
            }
            String secret = clientKey + key2;
            logger.info("客户端类型：" + c );
            Map<String, String> urlParams = URLUtils.getUrlParams(request.getQueryString());
            TreeMap<String,String> map = new TreeMap<>();
            map.putAll(urlParams);
            map.put(SysFinalValue.ACCESS_TOKEN,accessToken);
            map.put(SysFinalValue.CLIENT_TYPE,c);
            String s = SignUtils.signRequest(map, secret, uri);
            if(s.equals(sign)){
                return true;
            }else{
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().flush();
                response.getWriter().print(JSONObject.toJSON(new BaseResponse(400,"签名验证失败")));
                return  false;
            }
        }
        return  true;
    }


    private static String getClientKey(String c){
        switch (c){
            case "1": // android
                return "SCE54V";
            case "2": // IOS
                return "XTSFSN";
            case "3": // 网页
                return "RAV2X3";
            case "4": // 小程序
                return "O6HJAL";
            case "5": // 公众号
                return "NCX943";
            case "6": // PC端
                return "A5FW32";
        }
        return "";
    }
}
