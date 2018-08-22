package com.facetuis.server.app.web.response;

public class TeamOrderCountResponse {

    private Integer order_total; //	订单总人数
    private Integer order_today; // 今日订单数
    private Integer order_yesterday;//昨日订单数
    private Integer order_this_month;// 	昨日订单数
    private Integer order_last_month;// 本月订单数

    public Integer getOrder_total() {
        return order_total;
    }

    public void setOrder_total(Integer order_total) {
        this.order_total = order_total;
    }

    public Integer getOrder_today() {
        return order_today;
    }

    public void setOrder_today(Integer order_today) {
        this.order_today = order_today;
    }

    public Integer getOrder_yesterday() {
        return order_yesterday;
    }

    public void setOrder_yesterday(Integer order_yesterday) {
        this.order_yesterday = order_yesterday;
    }

    public Integer getOrder_this_month() {
        return order_this_month;
    }

    public void setOrder_this_month(Integer order_this_month) {
        this.order_this_month = order_this_month;
    }

    public Integer getOrder_last_month() {
        return order_last_month;
    }

    public void setOrder_last_month(Integer order_last_month) {
        this.order_last_month = order_last_month;
    }
}
