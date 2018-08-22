package com.facetuis.server.model.admin;

import com.facetuis.server.model.basic.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "t_admin_role")
@Entity
public class AdminRole extends BaseEntity {
     private String admin_id;
     private Integer role_id;
    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }
}
