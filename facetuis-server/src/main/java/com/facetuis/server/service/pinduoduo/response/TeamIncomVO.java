package com.facetuis.server.service.pinduoduo.response;

public class TeamIncomVO {

    private Long income_total;
    private Long income_today;
    private Long income_yesterday;
    private Long income_this_month;
    private Long income_last_month;

    public Long getIncome_total() {
        return income_total;
    }

    public void setIncome_total(Long income_total) {
        this.income_total = income_total;
    }

    public Long getIncome_today() {
        return income_today;
    }

    public void setIncome_today(Long income_today) {
        this.income_today = income_today;
    }

    public Long getIncome_yesterday() {
        return income_yesterday;
    }

    public void setIncome_yesterday(Long income_yesterday) {
        this.income_yesterday = income_yesterday;
    }

    public Long getIncome_this_month() {
        return income_this_month;
    }

    public void setIncome_this_month(Long income_this_month) {
        this.income_this_month = income_this_month;
    }

    public Long getIncome_last_month() {
        return income_last_month;
    }

    public void setIncome_last_month(Long income_last_month) {
        this.income_last_month = income_last_month;
    }
}
