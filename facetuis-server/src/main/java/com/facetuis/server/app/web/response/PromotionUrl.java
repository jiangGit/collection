package com.facetuis.server.app.web.response;

public class PromotionUrl {

    private String mobile_short_url;
    private String mobile_url;
    private String short_url;
    private String url;

    public String getMobile_short_url() {
        return mobile_short_url;
    }

    public void setMobile_short_url(String mobile_short_url) {
        this.mobile_short_url = mobile_short_url;
    }

    public String getMobile_url() {
        return mobile_url;
    }

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    public String getShort_url() {
        return short_url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
