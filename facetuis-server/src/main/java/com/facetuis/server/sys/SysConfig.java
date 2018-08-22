package com.facetuis.server.sys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SysConfig {

    @Value("${domain}")
    private String domain;


    /**
     * 拼接本服务器完整的接口地址
     * @param uri
     * @return
     */
    protected String getServerUrl(String uri){
        return domain + uri;
    }

}
