package com.facetuis.server.model.admin;

import com.facetuis.server.model.basic.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "t_admin_users")
@Entity
public class AdminUsers extends BaseEntity {
    private String userName;
    @JsonIgnore
    private String password;

    private String accessToken;
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
