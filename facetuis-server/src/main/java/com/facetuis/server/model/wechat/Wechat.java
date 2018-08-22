package com.facetuis.server.model.wechat;

import com.facetuis.server.model.basic.UUIDEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_wechat")
public class Wechat extends UUIDEntity {

    private String appid;

    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}
