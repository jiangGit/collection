package com.facetuis.server.service.reward.vo;

import java.util.Date;

public class RewardInvitingVO {

    private String invitingStart;
    private String invitingEnd;
    private Long totalAmount;
    private Long todayAmount;
    private Long yesterdayAmount;
    private Long monthAmount;

    public String getInvitingStart() {
        return invitingStart;
    }

    public void setInvitingStart(String invitingStart) {
        this.invitingStart = invitingStart;
    }

    public String getInvitingEnd() {
        return invitingEnd;
    }

    public void setInvitingEnd(String invitingEnd) {
        this.invitingEnd = invitingEnd;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getTodayAmount() {
        return todayAmount;
    }

    public void setTodayAmount(Long todayAmount) {
        this.todayAmount = todayAmount;
    }

    public Long getYesterdayAmount() {
        return yesterdayAmount;
    }

    public void setYesterdayAmount(Long yesterdayAmount) {
        this.yesterdayAmount = yesterdayAmount;
    }

    public Long getMonthAmount() {
        return monthAmount;
    }

    public void setMonthAmount(Long monthAmount) {
        this.monthAmount = monthAmount;
    }
}
