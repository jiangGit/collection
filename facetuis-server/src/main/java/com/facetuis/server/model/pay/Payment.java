package com.facetuis.server.model.pay;

import com.facetuis.server.model.basic.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "t_payment")
public class Payment extends BaseEntity {

    private String userId;
    private String productId;
    private String outTradeNo;// 订单号 自己订单号
    private String productTitle;//商品标题
    @Enumerated(EnumType.ORDINAL)
    private PayStatus payStatus;//支付状态
    @Enumerated(EnumType.ORDINAL)
    private PayType payType;//支付类型
    private String amount;// 价格

    private String tradeNo;// 支付平台中的流水号 微信、支付宝的交易流水号

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
 

    public PayStatus getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(PayStatus payStatus) {
        this.payStatus = payStatus;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }


    public String getProductTitle(){return productTitle;}

    public void setProductTitle(String title){this.productTitle=title;}
}
