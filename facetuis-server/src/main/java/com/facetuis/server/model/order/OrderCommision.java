package com.facetuis.server.model.order;

import com.facetuis.server.model.basic.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_order_commision")
public class OrderCommision extends BaseEntity{

    private String orderSn;
    private Integer orderStatus;
    private String pId;
    private Integer orderAmount;
    private Long orderCreateTime;
    private Long promotionRate;// 分拥比例

    private String userId;// 下订单用户
    private String user1Id;// 下单上级用户
    private String user2Id;// 下单上上级用户
    private String user3Id;// 下单上上上级用户

    private Long userCommision = 0L;// 下单用户分拥
    private Long user1Commision = 0L;// 下单上级用户分拥
    private Long user2Commision = 0L;// 下单上上级用户分拥
    private Long user3Commision = 0L;// 下单上上上级用户分拥

    private Boolean isCompute = false;// 是否分拥计算完成

    private Boolean isFinish = false;// 当前订单是否已经在个人佣金中完成订单结算 （是否已计算到可提现金额）
    private Boolean isWaitFinish = false;// 当前订单是否已经在个人佣金中完成订单待结算 （是否已计算到待结算）
    private Boolean isFailFinish = false;// 当前订单审核失败时，是否已经完成个人用户的订单佣金扣除

    public Boolean getFailFinish() {
        return isFailFinish;
    }

    public void setFailFinish(Boolean failFinish) {
        isFailFinish = failFinish;
    }

    public Boolean getWaitFinish() {
        return isWaitFinish == null ? false : isWaitFinish;
    }

    public void setWaitFinish(Boolean waitFinish) {
        isWaitFinish = waitFinish;
    }

    public Boolean getFinish() {
        return isFinish == null ? false : isFinish;
    }

    public void setFinish(Boolean finish) {
        isFinish = finish;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
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

    public Long getPromotionRate() {
        return promotionRate;
    }

    public void setPromotionRate(Long promotionRate) {
        this.promotionRate = promotionRate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUser1Id() {
        return user1Id;
    }

    public void setUser1Id(String user1Id) {
        this.user1Id = user1Id;
    }

    public String getUser2Id() {
        return user2Id;
    }

    public void setUser2Id(String user2Id) {
        this.user2Id = user2Id;
    }

    public String getUser3Id() {
        return user3Id;
    }

    public void setUser3Id(String user3Id) {
        this.user3Id = user3Id;
    }

    public Long getUserCommision() {
        return userCommision;
    }

    public void setUserCommision(Long userCommision) {
        this.userCommision = userCommision;
    }

    public Long getUser1Commision() {
        return user1Commision;
    }

    public void setUser1Commision(Long user1Commision) {
        this.user1Commision = user1Commision;
    }

    public Long getUser2Commision() {
        return user2Commision;
    }

    public void setUser2Commision(Long user2Commision) {
        this.user2Commision = user2Commision;
    }

    public Long getUser3Commision() {
        return user3Commision;
    }

    public void setUser3Commision(Long user3Commision) {
        this.user3Commision = user3Commision;
    }

    public Boolean getCompute() {
        return isCompute;
    }

    public void setCompute(Boolean compute) {
        isCompute = compute;
    }
}
