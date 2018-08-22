package com.facetuis.server.utils;


import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class RSA {


    public static void main(String[] args) {
        //字典序排序
        HashMap<String,String> map=new HashMap<String,String>();

        map.put("jsapi_ticket", "1");
        map.put("timestamp", "");
        map.put("nonceStr", "");
        map.put("url", "");

        Set<String> keyset= map.keySet();

        List list = new ArrayList<String>(keyset);

        Collections.sort(list);
//这种打印出的字符串顺序和微信官网提供的字典序顺序是一致的
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i)+"="+map.get(list.get(i)));
        }
    }


    /**
     * 参数签名加密
     * @param parameters request中的 所有 参数
     * @param secret 生成的key
     * @param uri request uri
     * @return
     */
    public static String signRequest(TreeMap<String,String> parameters,String secret,String uri){
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
        SignUtils.getSignStr(treeMap, sb);
        // 拼接Key
        sb.append("key").append("=").append(secret);
        //拼接 URI
        sb.append("&uri=").append(uri);
        //进行MD5加密
        String sign = MD5Utils.MD5(sb.toString());
        return sign;
    }
}
