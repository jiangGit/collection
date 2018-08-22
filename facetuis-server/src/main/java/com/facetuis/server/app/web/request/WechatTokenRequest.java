package com.facetuis.server.app.web.request;

import javax.validation.constraints.NotEmpty;

public class WechatTokenRequest {

    @NotEmpty
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
