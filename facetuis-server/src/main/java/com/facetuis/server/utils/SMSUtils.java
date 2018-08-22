package com.facetuis.server.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSUtils {

    private  static Map<String,Long> mobileList = new HashMap<>();

    public static boolean isSend(String mobile){
        clear();
        if(mobileList.containsKey(mobile)){
            Long sendTime = mobileList.get(mobile);
            Long now = System.currentTimeMillis();
            boolean isEx = now - sendTime > 1000 * 50;
            if( isEx ){
                mobileList.put(mobile,now);
                return true;
            }else{
                return false;
            }
        }else{
            mobileList.put(mobile,System.currentTimeMillis());
            return true;
        }
    }

    private static void clear(){
        if(mobileList.size() >= 5000){
            mobileList = new HashMap<>();
        }
    }

    public static boolean isChinaPhoneLegal(String str){
        String regExp = "^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }


}
