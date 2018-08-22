package com.facetuis.server.app.web.response;

public class UserRecommanderResponse {

    private String uuid;
    private String mobileNumber;
    private String headImg;//头像
    private String nickName;
    private String recommandCode;

    public String getRecommandCode() {
        if(recommandCode != null) {
            recommandCode = recommandCode.replaceAll(",", "");
        }
        return recommandCode;
    }

    public void setRecommandCode(String recommandCode) {
        this.recommandCode = recommandCode;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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
}
