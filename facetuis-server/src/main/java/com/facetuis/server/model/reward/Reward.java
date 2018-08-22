package com.facetuis.server.model.reward;

import com.facetuis.server.model.basic.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "t_reward")
public class Reward extends BaseEntity {

    private String userId;// 用户
    private Long amount;// 金额
    @Enumerated(EnumType.STRING)
    private RewardType rewardType;// 类型
    @Enumerated(EnumType.STRING)
    private RewardAction action;

    public RewardAction getAction() {
        return action;
    }

    public void setAction(RewardAction action) {
        this.action = action;
    }

    public RewardType getRewardType() {
        return rewardType;
    }

    public void setRewardType(RewardType rewardType) {
        this.rewardType = rewardType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
