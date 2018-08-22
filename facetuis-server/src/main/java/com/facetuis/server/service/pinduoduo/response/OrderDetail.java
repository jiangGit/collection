package com.facetuis.server.service.pinduoduo.response;

import com.alibaba.fastjson.annotation.JSONField;

public class OrderDetail {

    @JSONField(name = "order_sn")
    private String orderSn;
    @JSONField(name = "goods_id")
    private int goodsId;
    @JSONField(name = "goods_name")
    private String goodsNname;
    @JSONField(name = "goods_thumbnail_url")
    private String goodsThumbnailUrl;
    @JSONField(name = "goods_quantity")
    private int goodsQuantity;
    @JSONField(name = "goods_price")
    private int goodsPrice;
    @JSONField(name = "order_amount")
    private int orderAmount;
    @JSONField(name = "order_create_time")
    private long orderCreateTime;
    @JSONField(name = "order_verify_time")
    private long orderVerifyTime;
    @JSONField(name = "order_pay_time")
    private long ordePayTime;
    @JSONField(name = "promotion_rate")
    private long promotionRate;
    @JSONField(name = "promotion_amount")
    private long promotionAmount;
    @JSONField(name = "order_status")
    private int orderStatus;
    @JSONField(name = "order_status_desc")
    private String orderStatusDesc;
    @JSONField(name = "order_group_success_time")
    private String orderGroupSuccessTime;
    @JSONField(name = "order_modify_at")
    private String orderModifyAt;
    @JSONField(name = "p_id")
    private String pId;

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
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

    public int getGoodsQuantity() {
        return goodsQuantity;
    }

    public void setGoodsQuantity(int goodsQuantity) {
        this.goodsQuantity = goodsQuantity;
    }

    public int getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(int goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public long getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(long orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public long getOrderVerifyTime() {
        return orderVerifyTime;
    }

    public void setOrderVerifyTime(long orderVerifyTime) {
        this.orderVerifyTime = orderVerifyTime;
    }

    public long getOrdePayTime() {
        return ordePayTime;
    }

    public void setOrdePayTime(long ordePayTime) {
        this.ordePayTime = ordePayTime;
    }

    public long getPromotionRate() {
        return promotionRate;
    }

    public void setPromotionRate(long promotionRate) {
        this.promotionRate = promotionRate;
    }

    public long getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(long promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
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
