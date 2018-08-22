package com.facetuis.server.model.user;

import com.facetuis.server.model.basic.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "t_user_commision")
public class UserCommision extends BaseEntity {

    private String userId;
    private Long orderCash = 0l; // 订单可提现金额
    private Long waitSettlement = 0l; // 等待结算金额
    private Long finishSettlement = 0l;// 已完结结算金额
    @Enumerated(EnumType.STRING)
    private CashStatus cashStatus; // 提现状态
    private Long finishCash;// 已经提现金额
    private Long invitingCash = 0l;// 邀请用户奖励
    private Long updateCash = 0l;// 用户升级奖励
    private Integer invitingPeople = 0;// 邀请奖励用户人数

    public Integer getInvitingPeople() {
        return invitingPeople;
    }

    public void setInvitingPeople(Integer invitingPeople) {
        this.invitingPeople = invitingPeople;
    }

    public Long getInvitingCash() {
        return invitingCash;
    }

    public void setInvitingCash(Long invitingCash) {
        this.invitingCash = invitingCash;
    }

    public Long getFinishCash() {
        return  finishCash == null ? 0l : finishCash;
    }

    public void setFinishCash(Long finishCash) {
        this.finishCash = finishCash;
    }

    public CashStatus getCashStatus() {
        return cashStatus;
    }

    public void setCashStatus(CashStatus cashStatus) {
        this.cashStatus = cashStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getOrderCash() {
        return orderCash;
    }

    public void setOrderCash(Long orderCash) {
        this.orderCash = orderCash;
    }

    public Long getWaitSettlement() {
        return waitSettlement;
    }

    public void setWaitSettlement(Long waitSettlement) {
        this.waitSettlement = waitSettlement;
    }

    public Long getFinishSettlement() {
        return finishSettlement;
    }

    public void setFinishSettlement(Long finishSettlement) {
        this.finishSettlement = finishSettlement;
    }

    public Long getUpdateCash() {
        return updateCash;
    }

    public void setUpdateCash(Long updateCash) {
        this.updateCash = updateCash;
    }
}
