package com.facetuis.server.app.web.response;

import com.facetuis.server.utils.DateJsonTypeHHmm;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

public class TeamUsersResponse {

    @JsonProperty("header_img")
    private String headImg;//头像
    @JsonProperty("nick_name")
    private String nickName;
    @JsonProperty("mobile_number")
    private String mobileNumber;
    @JsonProperty("recommand_number")
    private Integer recommandNumber;
    @JsonSerialize(using = DateJsonTypeHHmm.class)
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Integer getRecommandNumber() {
        return recommandNumber;
    }

    public void setRecommandNumber(Integer recommandNumber) {
        this.recommandNumber = recommandNumber;
    }
}
