package com.facetuis.server.model.admin;

import com.facetuis.server.model.basic.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "t_permission")
@Entity
public class Permission extends BaseEntity {
    private String permission_name;
    private String detail;
    public String getPermission_name() {
        return permission_name;
    }

    public void setPermission_name(String permission_name) {
        this.permission_name = permission_name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
