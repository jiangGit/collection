package com.facetuis.server.app.web.request;

import javax.validation.constraints.NotNull;

public class UpgradedRequest {
    @NotNull
    private String productId;
    @NotNull
    private String outTradeNo;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
}
