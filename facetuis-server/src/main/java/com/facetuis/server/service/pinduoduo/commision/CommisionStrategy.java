package com.facetuis.server.service.pinduoduo.commision;

import com.facetuis.server.model.order.OrderCommision;
import com.facetuis.server.service.pinduoduo.response.OrderDetail;

public interface CommisionStrategy {

    /**
     * 计算佣金
     * @param orderDetail
     * @return
     */
    OrderCommision doCompute(OrderDetail orderDetail);
}
