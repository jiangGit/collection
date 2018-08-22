package com.facetuis.server.model.order;

import com.facetuis.server.model.basic.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_order")
public class Order extends BaseEntity{

    private String orderSn;
    private Integer goodsId;
    private String goodsNname;
    private String goodsThumbnailUrl;
    private Integer goodsQuantity;
    private Long goodsPrice;
    private Integer orderAmount;
    private Long orderCreateTime;
    private Long orderVerifyTime;
    private Long ordePayTime;
    private Long promotionRate;
    private Long promotionAmount;
    private Integer orderStatus;
    private String orderStatusDesc;
    private String orderGroupSuccessTime;
    private String orderModifyAt;
    private String pId;
    private Boolean enableCash = false;

    public Boolean getEnableCash() {
        return enableCash;
    }

    public void setEnableCash(Boolean enableCash) {
        this.enableCash = enableCash;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsNname() {
        return goodsNname;
    }

    public void setGoodsNname(String goodsNname) {
        this.goodsNname = goodsNname;
    }

    public String getGoodsThumbnailUrl() {
        return goodsThumbnailUrl;
    }

    public void setGoodsThumbnailUrl(String goodsThumbnailUrl) {
        this.goodsThumbnailUrl = goodsThumbnailUrl;
    }

    public Integer getGoodsQuantity() {
        return goodsQuantity;
    }

    public void setGoodsQuantity(Integer goodsQuantity) {
        this.goodsQuantity = goodsQuantity;
    }

    public Long getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Long goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Integer orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Long getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Long orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public Long getOrderVerifyTime() {
        return orderVerifyTime;
    }

    public void setOrderVerifyTime(Long orderVerifyTime) {
        this.orderVerifyTime = orderVerifyTime;
    }

    public Long getOrdePayTime() {
        return ordePayTime;
    }

    public void setOrdePayTime(Long ordePayTime) {
        this.ordePayTime = ordePayTime;
    }

    public Long getPromotionRate() {
        return promotionRate;
    }

    public void setPromotionRate(Long promotionRate) {
        this.promotionRate = promotionRate;
    }

    public Long getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(Long promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusDesc() {
        return orderStatusDesc;
    }

    public void setOrderStatusDesc(String orderStatusDesc) {
        this.orderStatusDesc = orderStatusDesc;
    }

    public String getOrderGroupSuccessTime() {
        return orderGroupSuccessTime;
    }

    public void setOrderGroupSuccessTime(String orderGroupSuccessTime) {
        this.orderGroupSuccessTime = orderGroupSuccessTime;
    }

    public String getOrderModifyAt() {
        return orderModifyAt;
    }

    public void setOrderModifyAt(String orderModifyAt) {
        this.orderModifyAt = orderModifyAt;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }
}
