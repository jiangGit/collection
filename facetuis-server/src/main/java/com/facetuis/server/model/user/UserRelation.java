package com.facetuis.server.model.user;


import com.facetuis.server.model.basic.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_users_relation")
public class UserRelation extends BaseEntity{
    private String userId;// 用户ID
    private String userLevel1Id;// 上级ID
    private String userLevel2Id;// 上级的上级ID
    private String userLevel3Id;// 上级的上级I的上级ID  团队ID
    @Column(columnDefinition = "TEXT")
    private String user1Ids;// 所属一级用户列表
    @Column(columnDefinition = "TEXT")
    private String user2Ids;// 所属二级用户列表
    @Column(columnDefinition = "TEXT")
    private String user3Ids;// 所属三级用户列表

    private String user1HighIds;// 直属高等级用户列表
    private Integer user1HighTotal = 0;// 直属高等级用户总人数

    private Integer user1Total = 0; // 所属一级总人数
    private Integer user2Total = 0; // 所属二级总人数
    private Integer user3Total = 0; // 所属三级总人数


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserLevel1Id() {
        return userLevel1Id;
    }

    public void setUserLevel1Id(String userLevel1Id) {
        this.userLevel1Id = userLevel1Id;
    }

    public String getUserLevel2Id() {
        return userLevel2Id;
    }

    public void setUserLevel2Id(String userLevel2Id) {
        this.userLevel2Id = userLevel2Id;
    }

    public String getUserLevel3Id() {
        return userLevel3Id;
    }

    public void setUserLevel3Id(String userLevel3Id) {
        this.userLevel3Id = userLevel3Id;
    }

    public String getUser1Ids() {
        return user1Ids;
    }

    public void setUser1Ids(String user1Ids) {
        this.user1Ids = user1Ids;
    }

    public String getUser2Ids() {
        return user2Ids;
    }

    public void setUser2Ids(String user2Ids) {
        this.user2Ids = user2Ids;
    }

    public String getUser3Ids() {
        return user3Ids;
    }

    public void setUser3Ids(String user3Ids) {
        this.user3Ids = user3Ids;
    }

    public String getUser1HighIds() {
        return user1HighIds;
    }

    public void setUser1HighIds(String user1HighIds) {
        this.user1HighIds = user1HighIds;
    }

    public Integer getUser1HighTotal() {
        return user1HighTotal;
    }

    public void setUser1HighTotal(Integer user1HighTotal) {
        this.user1HighTotal = user1HighTotal;
    }

    public Integer getUser1Total() {
        return user1Total;
    }

    public void setUser1Total(Integer user1Total) {
        this.user1Total = user1Total;
    }

    public Integer getUser2Total() {
        return user2Total;
    }

    public void setUser2Total(Integer user2Total) {
        this.user2Total = user2Total;
    }

    public Integer getUser3Total() {
        return user3Total;
    }

    public void setUser3Total(Integer user3Total) {
        this.user3Total = user3Total;
    }
}
