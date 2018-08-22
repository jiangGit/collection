package com.facetuis.server.service.user.utils;

import com.facetuis.server.utils.RandomUtils;
import org.springframework.stereotype.Component;

public class UserUtils {


    public static String generateRecommandCode(){
        return RandomUtils.rate(13000,9999999);
    }

    public static void main(String[] args) {
        System.out.println(generateRecommandCode());
    }

}
