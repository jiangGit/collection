package com.facetuis.server.service.sms.utils;

import com.facetuis.server.model.mobile.SmsModelCode;

import java.util.HashMap;
import java.util.Map;

public class SmsContent {

    private static Map<SmsModelCode,String> contents = new HashMap<>();

    static{
        if(contents == null || contents.size() == 0){
            contents.put(SmsModelCode.LOGIN,"您的验证码是：【变量】。请不要把验证码泄露给其他人。");
            contents.put(SmsModelCode.CASH,"您的验证码是：【变量】。打死都不能告诉别人哦！如非本人操作，可不用理会！");
            contents.put(SmsModelCode.REGISTER,"您的验证码是：【变量】。请不要把验证码泄露给其他人。");
        }
    }

    /**
     * 根据模块代码获取短信内容
     *
     * */
    public static String getContent(SmsModelCode code,String... var){
        return buzByCode(code,var);
    }


    private static String buzByCode(SmsModelCode code,String... var) {
        String content = contents.get(code);
        switch (code) {
            case CASH:
                content = content.replace("【变量】", var[0]);
                break;
            case LOGIN:
                content = content.replace("【变量】", var[0]);
                break;
            case REGISTER:
                content = content.replace("【变量】", var[0]);
                break;
            default:
                content = content;
                break;
        }
        return content;
    }





}
