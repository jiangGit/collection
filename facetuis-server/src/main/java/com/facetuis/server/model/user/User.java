package com.facetuis.server.model.user;

import com.facetuis.server.model.basic.BaseEntity;
import com.facetuis.server.utils.DateJsonTypeDay;
import com.facetuis.server.utils.DateJsonTypeHHmm;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Table(name = "t_users")
@Component
public class User extends BaseEntity {


    private String headImg;//头像
    private String nickName;
    private String openid;
    @JsonProperty("mobile_number")
    private String mobileNumber;
    @JsonProperty("invite_code")
    private String inviteCode; // 邀请码
    private String token; //  本系统token
    @JsonSerialize(using = DateJsonTypeHHmm.class)
    private Date loginTime;
    @JsonProperty("access_token")
    private String accessToken;//微信票据
    private Boolean enable = false;
    private UserLevel level = UserLevel.LEVEL1;
    @JsonProperty("level_txt")
    private String levelTxt;
    private Long loginExpiresIn = 1000 * 60 * 60 * 24 * 30l ;
    private String recommandCode;// 推荐码 属于自己的邀请码 拉下线使用
    private String levelUserId;// 上级用户ID
    private String unionId;// 微信统一开发者下的所有应用的用户唯一性ID
    private String pid;
    private String recommendUrl;
    @JsonSerialize(using = DateJsonTypeDay.class)
    private Date expireTime;
    private String deskAppToken;// 桌面应用token

    public String getDeskAppToken() {
        return deskAppToken;
    }

    public void setDeskAppToken(String deskAppToken) {
        this.deskAppToken = deskAppToken;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    private Boolean settingRQInGoodsImage = true;// 设置是否生成二维码图片到商品推广图中

    public Boolean getSettingRQInGoodsImage() {
        return settingRQInGoodsImage;
    }

    public void setSettingRQInGoodsImage(Boolean settingRQInGoodsImage) {
        this.settingRQInGoodsImage = settingRQInGoodsImage;
    }

    public String getRecommendUrl() {
        return recommendUrl;
    }

    public void setRecommendUrl(String recommendUrl) {
        this.recommendUrl = recommendUrl;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public UserLevel getLevel() {
        return level;
    }

    public void setLevel(UserLevel level) {
        this.level = level;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getLevelUserId() {
        return levelUserId;
    }

    public void setLevelUserId(String levelUserId) {
        this.levelUserId = levelUserId;
    }

    public Long getLoginExpiresIn() {
        return loginExpiresIn;
    }

    public void setLoginExpiresIn(Long loginExpiresIn) {
        this.loginExpiresIn = loginExpiresIn;
    }

    public String getRecommandCode() {
        return recommandCode;
    }

    public void setRecommandCode(String recommandCode) {
        this.recommandCode = recommandCode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }



    public String getLevelTxt() {
        return levelTxt;
    }

    public void setLevelTxt(String levelTxt) {
        this.levelTxt = levelTxt;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }


    public String getDisplayRecommend(){
        if(!StringUtils.isEmpty(this.recommandCode)){
            String[] split = this.recommandCode.split(",");
            for(int i = 0; i < split.length ; i ++){
                return split[split.length - 1];
            }
        }
        return "";
    }

   
}
