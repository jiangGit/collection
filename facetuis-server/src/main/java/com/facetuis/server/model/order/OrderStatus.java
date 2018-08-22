package com.facetuis.server.model.order;

public enum OrderStatus {
    NO_PAY(-1),// 未支付
    PAY(0),// 已支付
    TEAM(1), // 已成团
    CONFIRM(2), // 2-确认收货
    VERIFY_SUCCESS(3),//审核成功 100%会到账户
    VERIFY_FAIL(4),//审核失败（不可提现）
    SETTLEMENT(5),// 已结算 = 钱已到账户
    NO_DUODUO(8);



    private int status;

    private OrderStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static OrderStatus getStatus(int status) {
        for(OrderStatus s : OrderStatus.values()){
            if(status == s.getStatus()){
                return s;
            }
        }
        return null;
    }

}
