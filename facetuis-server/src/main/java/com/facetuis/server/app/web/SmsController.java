package com.facetuis.server.app.web;

import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import com.facetuis.server.model.mobile.SmsModelCode;
import com.facetuis.server.service.basic.BaseResult;
import com.facetuis.server.service.sms.SmsService;
import com.facetuis.server.utils.SMSUtils;
import com.facetuis.server.utils.SysFinalValue;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/1.0/sms")
public class SmsController extends FacetuisController {

    @Autowired
    private SmsService smsService;

    @Value("{sms.send.url}")
    private String appid;


    /**
     * 发送验证码
     * @param mobileNumber
     * @param modelCode
     * @return
     */
    @RequestMapping( value = "/{mobileNumber}/{modelCode}",method = RequestMethod.POST)
    public BaseResponse get(@PathVariable String mobileNumber, @PathVariable SmsModelCode modelCode, String captcha , HttpServletRequest request){
        if(SmsModelCode.REGISTER == modelCode){
            String attribute = request.getSession().getAttribute(SysFinalValue.SESSION_CAPTCHA_ID).toString();
            if(!attribute.equals(captcha)){
                return setErrorResult(400,"图片验证码错误");
            }
        }
        if(SMSUtils.isSend(mobileNumber)){
            BaseResult result = smsService.sendMessage(mobileNumber,modelCode);
            return onResult(result);
        }else{
            return new BaseResponse(600,"请1分钟后再发送！");
        }
    }

    @RequestMapping(value = "/check/{mobileNumber}/{modelCode}/code/{code}",method = RequestMethod.GET)
    public BaseResponse check(@PathVariable String mobileNumber, @PathVariable SmsModelCode modelCode ,@PathVariable String code){
        if(smsService.checkCode(mobileNumber,code,modelCode,false).hasError()){
            return new BaseResponse(600,"验证码错误");
        }
        return  successResult();
    }
}
