package com.facetuis.server.app.web;

import com.facetuis.server.service.basic.BaseResult;
import com.facetuis.server.service.wechat.WechatService;
import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import com.facetuis.server.app.web.request.WechatTokenRequest;
import com.facetuis.server.app.web.response.WechatTokenResponse;
import com.facetuis.server.service.wechat.response.MpGetUserInfoResponse;
import com.facetuis.server.service.wechat.vo.AccessTokenResponse;
import com.facetuis.server.utils.PayCommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/1.0/wechat")
public class WechatController extends FacetuisController {

    @Value("${wechat.mp.token}")
    private String token;
    @Value("${sys.web.register.url}")
    private String webRegister;

    @Autowired
    private WechatService wechatService;


    /**
     * 微信服务器调用->微信公众号token验证
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     * @return
     */
    @RequestMapping(value = "/mp/token",method = RequestMethod.GET)
    public String checkToken(String signature,String timestamp,String nonce,String echostr){
        boolean b = PayCommonUtil.checkSignature(signature, timestamp, nonce, token);
        if(b){
            return echostr;
        }
        return "";
    }

    /**
     *
     * 根据微信code获取微信AccessToken
     * @param code
     * @throws IOException
     */
    @RequestMapping(value = "/mp/token/{code}",method = RequestMethod.GET)
    public BaseResponse getCode(@PathVariable String code)  {
        BaseResult<AccessTokenResponse> token = wechatService.getMPToken(code);
        if(!token.hasError()){
            AccessTokenResponse response1 = token.getResult();
            response1.setAccess_token(token.getResult().getAccess_token());
            response1.setOpenid(token.getResult().getOpenid());
            response1.setUnionid(token.getResult().getUnionid());
            return successResult(response1);
        }
        return onResult(token);
    }




    /**
     * APP微信登陆 根据code获取accessToken
     * @param request
     * @param result
     * @return
     */
    @RequestMapping(value = "/token",method = RequestMethod.POST)
    public BaseResponse getToken(@RequestBody @Valid WechatTokenRequest request , BindingResult result){
        if(result.hasErrors()){
            return erroorResult(result);
        }
        WechatTokenResponse wechatTokenResponse = new WechatTokenResponse();
        BaseResult<AccessTokenResponse> baseResult = wechatService.getToken(request.getCode());
        if(baseResult.hasError()){
            return onResult(baseResult);
        }
        AccessTokenResponse response = baseResult.getResult();
        wechatTokenResponse.setAccess_token(response.getAccess_token());
        wechatTokenResponse.setOpenid(response.getOpenid());
        return successResult(wechatTokenResponse);
    }

}
