package com.facetuis.server.service.pinduoduo.scheduled;


import com.facetuis.server.service.pinduoduo.OrderService;
import com.facetuis.server.service.pinduoduo.response.OrderDetail;
import com.facetuis.server.service.pinduoduo.response.OrderListResponse;
import com.facetuis.server.service.pinduoduo.task.OrderTask;
import com.facetuis.server.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Component
public class OrderScheduled {

    private static final Logger logger = Logger.getLogger(OrderScheduled.class.getName());

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderTask orderTask;

    //上次更新时间
    private static Long updateTime = null ;

    // 时间间隔
    private static final long space = 1000 * 30 ;


    @Scheduled(fixedRate = 1000 * 20)
    public void syncByUpdateTime(){
        if(updateTime == null){
            // 启动第一次更新时长 3 个小时
            updateTime = System.currentTimeMillis()/1000 - 60 * 60 * 24 * 15;
        }
        long now = System.currentTimeMillis()/1000;
        syncOrdersByUpdate(updateTime + "",now + "");
        updateTime = now;
    }



    private void syncOrdersByUpdate(String startTime, String endTime) {
        boolean next = false;
        int page = 1;
        logger.info("同步订单：" + startTime + " | " + endTime );
        do {
            OrderListResponse response = orderService.getOrderByUpdate(startTime, endTime, "", page);
            next = syncBiz(response,startTime,endTime);
            page = page + 1;
        }while (next);
    }


    /**
     * 同步订单+佣金计算
     * 1.定时同步指定时间段内更新的订单
     * 2.将同步的订单进行佣金计算
     * @param response
     * @param startTime
     * @param endTime
     * @return
     */
    private boolean syncBiz(OrderListResponse response,String startTime,String endTime ){
        if(response == null || response.getOrder_list_get_response() == null){
            return false;
        }
        if(response.getOrder_list_get_response().getTotal_count() == 0){
            return false;
        }
        List<OrderDetail> order_list = response.getOrder_list_get_response().getOrder_list();
        logger.info("同步订单：" + startTime + " | " + endTime + " | 本次同步：" + order_list.size());
        if( order_list != null && order_list.size() != 0){
            // 同步订单
            orderTask.doUpdateOrderTask(response);
            // 计算订单佣金
            orderTask.doComputeOrderTask(response);
            // 计算用户佣金
            return true;
        }else{
            return false;
        }
    }

}
