package com.facetuis.server.service.pinduoduo.scheduled;

import com.facetuis.server.service.pinduoduo.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.logging.Logger;

public class OrderCashScheduled {
    protected final Logger logger = Logger.getLogger(this.getClass().getName());


    @Autowired
    private OrderService orderService;

    /**
     * 每月21号0点0分开始执行
     */
    @Scheduled(cron = "0 0 0 21 * ?")
    public void syncByUpdateTime(){

    }
}
