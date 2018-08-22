package com.facetuis.server.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreatUtil {
	//首字母转小写
	public static String toLowerOne(String s){
	  if(Character.isLowerCase(s.charAt(0))) {
	    return s;
	  }else {
	    return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	  }
	}
	//首字母转大写
	public static String toUpperOne(String s){
	  if(Character.isUpperCase(s.charAt(0))) {
		  return s;
	  }else {
	    return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	  }	
	}	
	//获取一级类目
    public static String getOneClass(String word){
    	List<Integer> list=new ArrayList<Integer>();
	    for(int i = 0; i < word.length(); i++){
		    if (Character.isUpperCase(word.charAt(i))){			    			    	
		    	list.add(i);
		    }		    
	    } 
	    if(list.size()<=1) {
	    	return word.toLowerCase();
	    }else if(list.size()==2){
	    	return word.substring(list.get(1), word.length()).toLowerCase();	 
	    }else {
	    	return word.substring(list.get(1), list.get(2)).toLowerCase();	 
	    }
    }
    //英译汉
    public static String getChinese(String english) {
    	if(english.contains("_") || english.contains(" ") || isUpperCase(english)) {
	   		if(english.contains("_")) {
	   			english=english.replace("_", " ");    			
	   		}
	   		if(isUpperCase(english)) {
	   			english=toLowerLine(english);
	   		}	   		
	   		String[] strArr=english.split(" "); 
	       	List<String> list= new ArrayList<String>();    	
	   		for (String string : strArr) {
	   			if(!string.isEmpty()) {
	   				list.add(string);
	   			}
	   		}
	   		String ret="";
	   		for (String str : list) {
	   			if (geDictionary().containsKey(str)) {
	   	        	ret+= geDictionary().get(str);
	   	        }
			}
	   		return ret;
    	}else {
            if (geDictionary().containsKey(english)) {
            	return geDictionary().get(english);
            } else {
            	return english;
            }	
    	}
    		   	
    }
    // 英译汉 词库
    public static Map<String, String> geDictionary() {
        Map<String, String> m = new HashMap<String, String>();
        m.put("dictionary", "字典");
        m.put("map", "地图");
        m.put("love", "爱");
        m.put("cat", "类别");
        m.put("dog", "狗");
        m.put("bird", "鸟");
        m.put("is", "是否");
        m.put("use", "可用");
        m.put("time", "时间");
        m.put("add", "添加");
        m.put("update", "更新");
        m.put("user", "用户");
        m.put("admin", "管理员");
        m.put("log", "日志");
        m.put("status", "状态");
        m.put("position", "位置");
        m.put("media", "媒介");
        m.put("type", "类型");
        m.put("ad", "广告");
        m.put("name", "名字");
        m.put("img", "图片");
        m.put("start", "开始");
        m.put("end", "结束");
        m.put("click", "点击");
        m.put("count", "次数");
        m.put("enabled", "是否可行");
        m.put("info", "信息");
        m.put("address", "地址");
        m.put("sex", "性别");
        m.put("phone", "手机");
        m.put("password", "密码");
        m.put("pwd", "密码");
        m.put("birthday", "生日");
        m.put("money", "资金");
        m.put("frozen", "冻结");
        m.put("rank", "等级");
        m.put("points", "积分");
        m.put("pay", "支付");
        m.put("last", "最后");
        m.put("validated", "验证");
        m.put("role", "角色名");
        m.put("permissions", "权限");
        m.put("home", "家");
        m.put("account", "账户");
        m.put("width", "宽度");
        m.put("height", "高度");
        m.put("desc", "描述");
        m.put("agency", "代理商");
        m.put("bank", "银行");
        m.put("price", "价格");
        m.put("order", "订单");
        m.put("goods", "商品");
        m.put("sort", "排序");
        m.put("val", "值");
        m.put("value", "值");
        m.put("title", "标题");
        m.put("number", "数量");
        m.put("num", "数量");
        m.put("dispose", "处理");
        m.put("unit", "单位");
        m.put("show", "显示");
        m.put("nav", "导航栏");
        m.put("in", "在");
        m.put("grade", "分级");
        m.put("comment", "评论");
        m.put("content", "内容");
        m.put("max", "最大");
        m.put("min", "最小");
        m.put("send", "发送");
        m.put("coupon", "优惠券");
        m.put("sn", "序号");
        m.put("suppliers", "供应商");
        m.put("parent", "上级");
        m.put("msg", "消息");
        m.put("sell", "销售");
        m.put("date", "日期");
        m.put("real", "现实");
        m.put("attr", "属性");
        m.put("step", "步骤");
        m.put("market", "市场");
        m.put("consignee", "收件人");
        m.put("insure", "保证");
        m.put("shipping", "物流");
        m.put("color", "颜色");
        m.put("code", "代码");
        m.put("action", "行动");
        m.put("login", "登陆");
        m.put("style", "风格");
        m.put("valid", "有效期");
        m.put("front", "正面");
        m.put("verso", "背面");
        return m;
    }
    // 英译汉  是否含有大写字母
    public static Boolean isUpperCase(String word){
	    for(int i = 0; i < word.length(); i++){
		    if (Character.isUpperCase(word.charAt(i))){			    			    	
		    	return true;
		    }		    
	    } 
	    return false;
    }
    // 英译汉   大写字母前面加空格并转小写
    public static  String toLowerLine(String param) {    
        Pattern  p=Pattern.compile("[A-Z]");    
        if(param==null ||param.equals("")){    
            return "";    
        }    
        StringBuilder builder=new StringBuilder(param);    
        Matcher mc=p.matcher(param);    
        int i=0;    
        while (mc.find()) {  
            builder.replace(mc.start()+i, mc.end()+i, " "+mc.group().toLowerCase());    
            i++;    
        }      
        if(' ' == builder.charAt(0)){    
            builder.deleteCharAt(0);    
        }    
        return builder.toString();    
    }  
}
