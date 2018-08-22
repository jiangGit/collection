package com.facetuis.server.service.payment.vo;

import java.io.Serializable;

public class UserCashVO implements Serializable{

    private String amount;// 可提现
    private String settlementAmount;// 已结算金额
    private String waitAmount;//待结算

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSettlementAmount() {
        return settlementAmount;
    }

    public void setSettlementAmount(String settlementAmount) {
        this.settlementAmount = settlementAmount;
    }

    public String getWaitAmount() {
        return waitAmount;
    }

    public void setWaitAmount(String waitAmount) {
        this.waitAmount = waitAmount;
    }
}
