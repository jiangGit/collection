package com.facetuis.server.app.web;

import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import com.facetuis.server.dao.order.OrderRepository;
import com.facetuis.server.model.order.Order;
import com.facetuis.server.model.user.User;
import com.facetuis.server.service.pinduoduo.OrderService;
import com.facetuis.server.service.pinduoduo.response.OrderVO;
import com.facetuis.server.utils.NeedLogin;
import com.facetuis.server.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/1.0/order")
public class OrderController extends FacetuisController {


    @Autowired
    private OrderService orderService;

    /**
     * 获取我的订单
     * @param orderStatus
     * @param n
     * @param m
     * @return
     */
    @RequestMapping(value = "/my/{orderStatus}",method = RequestMethod.GET)
    @NeedLogin(needLogin = true)
    public BaseResponse getMyOrder(@PathVariable Integer orderStatus , String n, String m,Pageable pageRequest){
        User user = getUser(); // 获取当前登录用户
        //获取用户的pid
        String uPid = user.getPid();
        //查询   根据用户pid查询出所有订单数据
        Page<Order> page= orderService.getOrdersByPid(uPid,pageRequest,orderStatus);
        return successResult(page);
    }
}
