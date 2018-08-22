package com.facetuis.server.service.pinduoduo.commision;

import com.facetuis.server.dao.order.OrderCommisionRepository;
import com.facetuis.server.model.order.OrderCommision;
import com.facetuis.server.service.pinduoduo.response.OrderDetail;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CommisionSubStrategy implements CommisionStrategy {

    @Autowired
    private OrderCommisionRepository orderCommisionRepository;

    @Override
    public OrderCommision doCompute(final OrderDetail orderDetail) {
        OrderCommision orderCommision = orderCommisionRepository.findByOrderSn(orderDetail.getOrderSn());
        if(orderCommision == null){
            orderCommision = new OrderCommision();
            orderCommision.setUuid(UUID.randomUUID().toString());
            BeanUtils.copyProperties(orderDetail,orderCommision);
        }
        orderCommision.setUserCommision(0L);
        orderCommision.setUser1Commision(0L);
        orderCommision.setUser2Commision(0L);
        orderCommision.setUser3Commision(0L);
        return orderCommision;
    }
}
