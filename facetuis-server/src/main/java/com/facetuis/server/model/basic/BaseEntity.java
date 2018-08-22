package com.facetuis.server.model.basic;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@DynamicInsert
public class BaseEntity extends UUIDEntity {
    @Column(name="create_time",nullable=false)
    @JsonIgnore
    private Date createTime = new Date();// 创建时间
    @JsonIgnore
    @Column(name="update_time")
    private Date updateTime = new Date();// 更新时间
    @JsonIgnore
    @Column(name="delete_time")
    private Date deleteTime;// 删除时间
    @JsonIgnore
    @Column(columnDefinition="int default 0",name="is_delete",nullable = false)
    private Integer isDelete = 0;// 是否删除  {0 ： 未删除   1 ：已删除}

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
