package com.facetuis.server.dao.order;

import com.facetuis.server.model.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,String> {

    List<Order> findByOrderSnIn(List<String> orderSn);

    Order findByOrderSn(String orderSn);

    /**
     * 物理删除
     * @param orderSn
     */
    void deleteByOrderSnIn(List<String> orderSn);


    @Query(value = "select * from t_order where p_id in ?1 and  order_create_time > ?2  and  order_create_time < ?3 order by order_create_time DESC ",nativeQuery = true)
    Page<Order> findOrder(List<String> pids, long startTime, long endTime, Pageable pageable);



    @Query(value = "select * from t_order where p_id in ?1 and  order_create_time > ?2  and  order_create_time < ?3 and order_status = ?4 order by order_create_time DESC  ",nativeQuery = true)
    Page<Order> findOrderByStatus(List<String> pids, long startTime, long endTime,int status, Pageable pageable);



    Page<Order> findByPId(String pid,Pageable pageable);

    Page<Order> findByPIdAndOrderStatus(String pid, Integer status, Pageable pageable);
}
