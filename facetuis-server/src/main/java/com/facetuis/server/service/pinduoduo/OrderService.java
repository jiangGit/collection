package com.facetuis.server.service.pinduoduo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.facetuis.server.dao.order.OrderRepository;
import com.facetuis.server.dao.user.UserRelationRepository;
import com.facetuis.server.dao.user.UserRepository;
import com.facetuis.server.model.order.Order;
import com.facetuis.server.model.user.User;
import com.facetuis.server.model.user.UserRelation;
import com.facetuis.server.service.basic.BaseResult;
import com.facetuis.server.service.basic.BasicService;
import com.facetuis.server.service.pinduoduo.response.OrderDetail;
import com.facetuis.server.service.pinduoduo.response.OrderListResponse;
import com.facetuis.server.service.pinduoduo.response.OrderVO;
import com.facetuis.server.service.pinduoduo.utils.PRequestUtils;
import com.facetuis.server.utils.TimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.logging.Logger;

@Service
public class OrderService extends BasicService {

    private static final Logger logger = Logger.getLogger("订单业务逻辑");

    @Autowired
    private PRequestUtils pRequestUtils;

    private String API_ORDER_RANGE = "pdd.ddk.order.list.range.get";

    private String API_ORDER_RANGE_UPDATE = "pdd.ddk.order.list.increment.get";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRelationRepository userRelationRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * 查询订单-订单创建时间
     * @param startTime
     * @param endTime
     * @param pid
     * @param page
     * @return
     */
    public OrderListResponse getOrderByAdd(String startTime, String endTime, String pid, int page) {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("start_time", startTime);
        map.put("end_time", endTime);
        map.put("p_id", pid);
        map.put("page", page + "");
        map.put("page_size", "50");
        BaseResult<String> send = pRequestUtils.send(API_ORDER_RANGE, map);
        OrderListResponse response = JSONObject.parseObject(send.getResult(), OrderListResponse.class);
        return response;
    }

    /**
     * 查询订单-订单更新时间
     * @param startTime
     * @param endTime
     * @param pid
     * @param page
     * @return
     */
    public OrderListResponse getOrderByUpdate(String startTime, String endTime, String pid, int page) {
        SortedMap<String, String> map = new TreeMap<>();
        map.put("start_update_time", startTime);
        map.put("end_update_time", endTime);
        map.put("p_id", pid);
        map.put("page", page + "");
        map.put("page_size", "50");
        BaseResult<String> send = pRequestUtils.send(API_ORDER_RANGE_UPDATE, map);
        OrderListResponse response = JSONObject.parseObject(send.getResult(), OrderListResponse.class);
        return response;
    }

    /**
     * 批量更新订单
     * 删除： 更新逻辑：先删除根据订单号查询到的订单 ---> 新增所有订单
     * 现在：根据订单号查询订单是否已存在，如果存在则更新，如果不存在则新增
     * @param orderList
     */
    @Transactional
    public void updateOrder(OrderListResponse orderList){
        if(orderList == null || orderList.getOrder_list_get_response() == null){
            return;
        }
        List<OrderDetail> order_list = orderList.getOrder_list_get_response().getOrder_list();
        if(order_list != null && order_list.size() > 0){
            for(OrderDetail order : order_list){
                //  根据orderSn 查询是否订单已存在
                Order o = orderRepository.findByOrderSn(order.getOrderSn());
                if(o == null){
                    o = new Order();
                    o.setUuid(UUID.randomUUID().toString());
                    o.setCreateTime(new Date());
                    BeanUtils.copyProperties(order,o);
                    o.setOrderStatus(order.getOrderStatus());
                    o.setGoodsPrice(new Long(order.getGoodsPrice()));
                    orderRepository.save(o);
                }else{
                    o.setUpdateTime(new Date());
                    BeanUtils.copyProperties(order,o);
                    orderRepository.save(o);
                }
            }
        }
    }


    /**
     * 获取团队所有订单
     * @param userId
     * @return
     */
    public Page<Order> findTeamOrders(String userId,Pageable pageable){
        String startTime = TimeUtils.date2String(TimeUtils.getDateBefore(new Date(), 60)) + " 00:00:00";
        String endTime = TimeUtils.date2String(new Date()) + " 23:59:59";
        return findByDate(startTime,endTime,userId,pageable);
    }


    /**
     * 根据指定日期获取团队订单
     * @param startTime
     * @param endTime
     * @param userId
     * @return
     */
    public Page<Order> findByDate(String startTime, String endTime, String userId, Pageable pageable){
         long star =  TimeUtils.stringToDateTime(startTime).getTime()/1000;
         long end = TimeUtils.stringToDateTime(endTime).getTime()/1000;
        List<String> pids = getTeamPids(userId);
        if(pids.size() == 0){
            logger.info("未找到推广位");
            return new PageImpl<>(Collections.EMPTY_LIST);
        }
        System.out.println("star :: " + star + " | end :: " + end + " | pid :: " + pids.get(0));
        Page<Order> orders = orderRepository.findOrder(pids, star, end,pageable);
        return orders;
    }


    /**
     * 根据指定日期获取团队订单
     * @param startTime
     * @param endTime
     * @param userId
     * @return
     */
    public Page<Order> findByDateAndStatus(String startTime, String endTime, String userId,int status ,Pageable pageable){
        long star =  TimeUtils.stringToDateTime(startTime).getTime()/1000;
        long end = TimeUtils.stringToDateTime(endTime).getTime()/1000;
        List<String> pids = getTeamPids(userId);
        if(pids.size() == 0){
            logger.info("未找到推广位");
            return new PageImpl<>(Collections.EMPTY_LIST);
        }
        Page<Order> orders = orderRepository.findOrderByStatus(pids, star, end,status,pageable);
        return orders;
    }



    /**
     * 根据订单状态查询
     * @param userId
     * @param status
     * @param pageable
     * @return
     */
    public Page<OrderVO> findByStatus(String userId, int status, Pageable pageable){
        Page<Order> orders = null;
        if(status == -9){
            orders = findTeamOrders(userId, pageable);
        }else{
            String startTime = TimeUtils.date2String(TimeUtils.getDateBefore(new Date(), 60)) + " 00:00:00";
            String endTime = TimeUtils.date2String(new Date()) + " 23:59:59";
            orders = findByDateAndStatus(startTime,endTime,userId,status,pageable);
        }
        if(orders == null){
            return new PageImpl<OrderVO>(Collections.EMPTY_LIST);
        }
        return getOrderVo(orders.getContent());
    }


    /**
     * 查询团队订单
     * @param userId
     * @param mobile
     * @param nickName
     * @param status
     * @param pageable
     * @return
     */
    public Page<OrderVO> findByMobileOrNickName(String userId, String mobile,String nickName,int status,Pageable pageable){
        UserRelation userRelation = userRelationRepository.findByUserId(userId);// 当前用户的所有团队成员
        String user3Ids = userRelation.getUser3Ids();
        String user2Ids = userRelation.getUser2Ids();
        String user1Ids = userRelation.getUser1Ids();
        List<String> userPids = new ArrayList<>();
        String searchUserId = "";
        if(!StringUtils.isEmpty(mobile)){
            User user = userRepository.findByMobileNumber(mobile);
            userPids.add(user.getPid());
            searchUserId = user.getUuid();
        }else if(!StringUtils.isEmpty(nickName)){
            List<User> byNickNameLike = userRepository.findByNickName("%" + nickName + "%");
            for(User user : byNickNameLike) {
                userPids.add(user.getPid());
            }
        }
        if(userPids.size() == 0){
            return new PageImpl<OrderVO>(Collections.EMPTY_LIST) ;
        }
        if( (user1Ids != null && user1Ids.contains(searchUserId)) || (user2Ids != null && user2Ids.contains(searchUserId)) ||  (user3Ids != null && user3Ids.contains(searchUserId)) ){
            String startTime = TimeUtils.date2String(TimeUtils.getDateBefore(new Date(), 60)) + " 00:00:00";
            String endTime = TimeUtils.date2String(new Date()) + " 23:59:59";
            long star =  TimeUtils.stringToDateTime(startTime).getTime()/1000;
            long end = TimeUtils.stringToDateTime(endTime).getTime()/1000;
            Page<Order> orders = null;
            if(status != -9) {
                 orders = orderRepository.findOrderByStatus(userPids, star, end, status, pageable);
            }else{
                 orders = orderRepository.findOrder(userPids, star, end, pageable);
            }
            return getOrderVo(orders.getContent());
        }
        return  new PageImpl<OrderVO>(Collections.EMPTY_LIST) ;
    }

    /**
     * 获取团队所有推广位
     * @param userId
     * @return
     */
    private List<String> getTeamPids(String userId){
        UserRelation userRelation = userRelationRepository.findByUserId(userId);// 当前用户的所有团队成员
        if(userRelation == null){
            logger.info("未找到团队");
            return Collections.EMPTY_LIST;
        }
        String user1Ids = userRelation.getUser1Ids();// 所属一级用户
        System.out.println("所属一级用户 :: " + user1Ids );
        List<String> user1s = new ArrayList<>();
        getUserIds(user1Ids, user1s);
        String user2Ids = userRelation.getUser2Ids();// 所属二级用户
        System.out.println("所属二级用户 :: " + user2Ids );
        List<String> user2s = new ArrayList<>();
        getUserIds(user2Ids, user2s);
        String user3Ids = userRelation.getUser3Ids();// 所属三级用户
        System.out.println("所属三级用户 :: " + user3Ids );
        List<String> user3s = new ArrayList<>();
        getUserIds(user3Ids, user3s);
        user1s.addAll(user2s);
        user1s.addAll(user3s);
        System.out.println("user1s :: " + JSONArray.toJSONString(user1s) + " | " );
        List<User> allUsers = userRepository.findAllById(user1s);
        List<String> pids = new ArrayList<>();
        for(User user : allUsers){
            System.out.println("user pid :: " + user.getUuid() + " = " + user.getPid() + " | " );
            pids.add(user.getPid());
        }
        System.out.println("pids :: " + pids.toArray()  + " | " + pids.size());
        return pids;
    }

    private void getUserIds(String userIds, List<String> users) {
        if(!StringUtils.isEmpty(userIds)){
            String[] split = userIds.split(",");
            for(String s : split){
                if(!StringUtils.isEmpty(s)) {
                    users.add(s);
                }
            }
        }
    }

    public Page<OrderVO> getOrderVo(List<Order> content){
        if(content.size() > 0){
            List<OrderVO> list = new ArrayList<>();
            for(Order order : content){
                OrderVO vo = new OrderVO();
                User user = userRepository.findByPid(order.getpId());
                if(user != null){
                    BeanUtils.copyProperties(user,vo);
                    vo.setUserId(user.getUuid());
                }
                BeanUtils.copyProperties(order,vo);
                list.add(vo);
            }
            return new PageImpl<OrderVO>(list);
        }
        return new PageImpl<OrderVO>(Collections.EMPTY_LIST);
    }
    /* 根据pid获取所有订单

     */

    public Page<Order> getOrdersByPid(String pid,Pageable pageable,Integer orderStatus)
    {
        if(orderStatus == -9){
            return orderRepository.findByPId(pid, pageable);
        }else{
            return orderRepository.findByPIdAndOrderStatus(pid,orderStatus, pageable);
        }

    }

    public static void main(String[] args) {
        List<String> a = new ArrayList<>();
        List<String> b = new ArrayList<>();
        a.addAll(b);
        int i = 0;

    }

}
