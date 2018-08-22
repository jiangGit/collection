package com.facetuis.server.service.sms;

import com.alibaba.fastjson.JSONObject;
import com.facetuis.server.dao.sms.SmsMessageRepository;
import com.facetuis.server.model.mobile.SmsMessage;
import com.facetuis.server.model.mobile.SmsModelCode;
import com.facetuis.server.service.basic.BaseResult;
import com.facetuis.server.service.basic.BasicService;
import com.facetuis.server.service.sms.utils.SmsContent;
import com.facetuis.server.service.sms.vo.SmsSubmitResponse;
import com.facetuis.server.utils.RandomUtils;
import org.apache.http.client.fluent.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

@Service
public class SmsService  extends BasicService {

    private static final Logger logger = Logger.getLogger(SmsSubmitResponse.class.getName());

    @Autowired
    private SmsMessageRepository smsMessageRepository;

    @Value("${sms.send.url}")
    private String smsUrl;
    @Value("${sms.app.id}")
    private String smsAppID;
    @Value("${sms.app.secret}")
    private String smsAppKey;
    @Value("${sys.is.open.mobile.check}")
    private String isOpenCheck;


    public BaseResult sendMessage(String mobile,SmsModelCode modelCode){
        String vcode  = RandomUtils.randomNumber(6);// 验证码
        String content  = SmsContent.getContent(modelCode,vcode);// 短信内容
        String url = String.format(smsUrl + "&account=%s&password=%s&mobile=%s&content=%s&format=json",smsAppID,smsAppKey,mobile,content);
        String response = "";
        try {
            response = Request.Get(url).execute().returnContent().asString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!StringUtils.isEmpty(response)){
            SmsSubmitResponse smsSubmitResponse = JSONObject.parseObject(response, SmsSubmitResponse.class);
            if(smsSubmitResponse != null){
                SmsMessage smsMessage = new SmsMessage();
                smsMessage.setCode(vcode);
                smsMessage.setModelCode(modelCode);
                smsMessage.setMobileNumber(mobile);
                smsMessage.setExpiresIn(60);
                smsMessage.setRead(false);
                smsMessage.setMessage(content);
                smsMessage.setSmsid(smsSubmitResponse.getSmsid());
                smsMessage.setSmsCode(smsSubmitResponse.getCode());
                smsMessage.setUuid(UUID.randomUUID().toString());
                smsMessage.setCreateTime(new Date());
                smsMessageRepository.save(smsMessage);
                if(smsSubmitResponse.getCode() == 2){
                    logger.info("短信发送成功 :: " + smsSubmitResponse.getSmsid());
                    return new BaseResult();
                }else{
                    return new BaseResult(600,"SMS code : " + smsSubmitResponse.getCode() + " | msg : " + smsSubmitResponse.getMsg());
                }
            }
        }
        return null;
    }


    /**
     * 校验验证码
     *
     * @param mobile
     * @param code
     * @param changeRead true 验证过期时间和是否已读 false:直接判断验证码
     * @return
     */
    public BaseResult checkCode(String mobile,String code, SmsModelCode modelCode, boolean changeRead){
        if( !Boolean.parseBoolean(isOpenCheck)){
            return new BaseResult();
        }
        SmsMessage smsMessage = smsMessageRepository.findByMobileNumberAndModelCode(mobile,modelCode);
        if(smsMessage == null){
            return new BaseResult(600,"未找到手机号，验证失败");
        }

        String vCode = smsMessage.getCode();
        if(changeRead){
            if(smsMessage.getRead()){
                return new BaseResult(600,"验证码不能重复验证");
            }
            int expiresIn = smsMessage.getExpiresIn();
            Long createTime = smsMessage.getCreateTime().getTime();
            Long now = System.currentTimeMillis();
            Long difference =  now - createTime; // 实际时间差
            if(difference.intValue() >  expiresIn){
                return new BaseResult(600,"验证码失效");
            }
            if(vCode.equals(code)){
                smsMessage.setRead(true);
                smsMessageRepository.save(smsMessage);
                return new BaseResult();
            }else{
                return new BaseResult(600,"验证码校验失败");
            }
        }else{
            if(vCode.equals(code)){
                return new BaseResult();
            }else{
                return new BaseResult(600,"验证码校验失败");
            }
        }
    }


}
