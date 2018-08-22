package com.facetuis.server.model.pay;

import com.facetuis.server.model.basic.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * 体现请求
 */
@Entity
@Table(name = "t_withdraw_cash")
public class WithdrawCashRequest extends BaseEntity {

    private String userId;
    private Long amount;
    @JsonIgnore
    @Enumerated(EnumType.ORDINAL)
    private CashStatus status;
    private Date withdrawTime;// 提现时间
    @NotEmpty
    private String aliPayAccount;//支付宝帐号
    @NotEmpty
    private String userName;// 提现姓名

    public String getAliPayAccount() {
        return aliPayAccount;
    }

    public void setAliPayAccount(String aliPayAccount) {
        this.aliPayAccount = aliPayAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getWithdrawTime() {
        return withdrawTime;
    }

    public void setWithdrawTime(Date withdrawTime) {
        this.withdrawTime = withdrawTime;
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

    public CashStatus getStatus() {
        return status;
    }

    public void setStatus(CashStatus status) {
        this.status = status;
    }
}
