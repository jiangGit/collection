package com.facetuis.server.utils;

import org.springframework.util.StringUtils;

import java.util.*;

public class SignUtils {

    public static String signRequest(TreeMap<String,String> parameters, String secret, String uri){
        // 按照升序排序
        TreeMap<String, String> treeMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        treeMap = (TreeMap<String, String>) parameters;
        StringBuffer sb = new StringBuffer();
        //把map中的集合拼接成字符串
        getSignStr(treeMap, sb);
        // 拼接Key
        sb.append("key").append("=").append(secret);
        //拼接 URI
        sb.append("&uri=").append(uri);
        //进行MD5加密
        String sign = MD5Utils.MD5(sb.toString()).toUpperCase();
        return sign;
    }

    static void getSignStr(TreeMap<String, String> treeMap, StringBuffer sb) {
        for(Map.Entry<String, String> entry:treeMap.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();
            if(!StringUtils.isEmpty(value)  && !"sign".equals(key) ){
                sb.append(key).append("=").append(value).append("&");
            }
        }
    }


}
