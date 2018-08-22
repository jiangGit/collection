package com.facetuis.server.service.pinduoduo.response;

import java.util.List;

public class OrderListBody {

    private int total_count;
    private List<OrderDetail> order_list;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public List<OrderDetail> getOrder_list() {
        return order_list;
    }

    public void setOrder_list(List<OrderDetail> order_list) {
        this.order_list = order_list;
    }
}
