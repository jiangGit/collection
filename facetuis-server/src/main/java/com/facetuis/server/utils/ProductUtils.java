package com.facetuis.server.utils;

import com.facetuis.server.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductUtils {
    private static  Product productNo1 = null;
    private static  Product productNo2 = null;
    private static  Product productNo3 = null;

    static {
        if(productNo1 == null){
            productNo1 = new Product();
            productNo1.setUuid("1");
            productNo1.setAmount("1");
            productNo1.setTimeLimit("月");
            productNo1.setTimeLimitValue(1);
            productNo1.setTimeLimitTxt("一个月");
            productNo1.setTitle("SVIP总监一个月");
            productNo1.setValues(30);
        }

        if(productNo2 == null){
            productNo2 = new Product();
            productNo2.setUuid("2");
            productNo2.setAmount("1");
            productNo2.setTimeLimit("月");
            productNo2.setTimeLimitValue(1);
            productNo2.setTimeLimitTxt("一个月");
            productNo2.setTitle("SVIP总监一个月");
            productNo2.setValues(30);
        }

        if(productNo3 == null){
            productNo3 = new Product();
            productNo3.setUuid("3");
            productNo3.setAmount("1");
            productNo3.setTimeLimit("年");
            productNo3.setTimeLimitValue(1);
            productNo3.setTimeLimitTxt("永久");
            productNo3.setTitle("无邀请码超级团长永久");
            productNo3.setValues(365);
        }
    }


    public static List<Product> getList(){
        List<Product> list = new ArrayList<>();
        list.add(productNo1);
        list.add(productNo2);
        list.add(productNo3);
        return list;
    }

    public static Product getProduct(String productId){
        if(productId.equals("1")){
            return productNo1;
        }
        if(productId.equals("2")){
            return productNo2;
        }
        if(productId.equals("3")){
            return productNo3;
        }
        return null;
    }

    public static void main(String[] args) {
        String rate = RandomUtils.rate(2000, 8000);

    }
}
