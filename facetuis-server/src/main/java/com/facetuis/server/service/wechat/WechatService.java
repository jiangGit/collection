package com.facetuis.server.service.wechat;

import com.alibaba.fastjson.JSONObject;
import com.facetuis.server.service.basic.BaseResult;
import com.facetuis.server.service.basic.BasicService;
import com.facetuis.server.service.wechat.response.MpGetUserInfoResponse;
import com.facetuis.server.service.wechat.vo.AccessTokenResponse;
import com.facetuis.server.app.web.basic.BaseResponse;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.logging.Logger;

@Service
public class WechatService extends BasicService {

    private static final Logger logger = Logger.getLogger(WechatService.class.getName());

    @Value("${wechat.access.token.url}")
    private String GET_TOKEN_URL ;
    @Value("${wechat.access.token.refresh.url}")
    private String refreshUrl;
    @Value("${wechat.app.id}")
    private String WECHAT_APPID;
    @Value("${wechat.app.mp.id}")
    private String WECHAT_MP_APPID;
    @Value("${wechat.app.mp.secret}")
    private String WECHAT_MP_SECRET;
    @Value("${wechat.app.secret}")
    private String WECHAT_SECRET;
    @Value("${wechat.mp.user.info}")
    private String GET_USER_INFO;


    /**
     * 公众号
     * @param code
     * @return
     */
    public  BaseResult<AccessTokenResponse> getMPToken(String code){
        return wchatToken(code,WECHAT_MP_APPID,WECHAT_MP_SECRET);
    }


    /**
     * 微信移动应用
     * @param code
     * @return
     */
    // 根据CODE获取AccessToken
    public  BaseResult<AccessTokenResponse> getToken(String code){
        return wchatToken(code,WECHAT_APPID,WECHAT_SECRET);
    }

    /**
     *
     * 支持微信公众号、微信移动应用根据code获取accessToken
     * @param code
     * @param appid
     * @param secret
     * @return
     */
    private  BaseResult<AccessTokenResponse> wchatToken(String code,String appid,String secret){
        BaseResult result = new BaseResult();
        String  url = String.format(GET_TOKEN_URL + "?appid=%s&secret=%s&code=%s&grant_type=authorization_code",appid,secret,code);
        String response = null;
        try{
            response =  Request.Get(url).execute().returnContent().asString();
        }catch (IOException e){
            e.printStackTrace();
            result.setCode(500);
            result.setMessage(e.getMessage());
            return result;
        }
        if(!StringUtils.isEmpty(response)){
            AccessTokenResponse accessTokenResponse = JSONObject.parseObject(response, AccessTokenResponse.class);
            logger.info("GET ACCESS_TOKEN :: " + response);
            accessTokenResponse = refreshToken(accessTokenResponse.getRefresh_token(),appid);
            result.setResult(accessTokenResponse);
        }else{
            result.setCode(600);
            result.setMessage("业务处理失败，请稍后再试！");
        }
        return result;
    }


    /**
     * 微信公众号获取用户信息(微信移动应用通过客户端SDK获取用户信息，无服务端API)
     * @param access_token
     * @param openid
     * @return
     */
    public BaseResult<MpGetUserInfoResponse > getUserInfo(String access_token,String openid){
        BaseResult<MpGetUserInfoResponse> result = new BaseResult<>();
        String  url = String.format(GET_USER_INFO,access_token,openid);
        String response = null;
        try{
            response =  Request.Get(url).execute().returnContent().asString();
            if(!StringUtils.isEmpty(response)){
                logger.info("根据公众号信息获取用户信息 userInfo :: " + response);
                MpGetUserInfoResponse accessTokenResponse = JSONObject.parseObject(response, MpGetUserInfoResponse.class);
                if(accessTokenResponse != null && accessTokenResponse.getNickname() != null) {
                    accessTokenResponse.setNickname(new String(accessTokenResponse.getNickname().getBytes("ISO-8859-1"), "UTF-8"));
                }
                result.setResult(accessTokenResponse);
            }else{
                result.setCode(600);
                result.setMessage("业务处理失败，请稍后再试！");
            }
            return result;
        }catch (IOException e){
            e.printStackTrace();
            return new BaseResult(600,e.getMessage());
        }
    }


    public AccessTokenResponse refreshToken(String refreshToken,String appid){
        String  url = String.format(refreshUrl + "?appid=%s&grant_type=refresh_token&refresh_token=%s",appid,refreshToken);
        String response = null;
        try{
            response =  Request.Get(url).execute().returnContent().asString();
            AccessTokenResponse accessTokenResponse = JSONObject.parseObject(response, AccessTokenResponse.class);
            logger.info("刷新ACCESS_TOKEN ::" + response);
            return accessTokenResponse;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
