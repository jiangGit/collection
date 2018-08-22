package com.facetuis.server.service.pinduoduo.commision;

import com.facetuis.server.dao.order.OrderCommisionRepository;
import com.facetuis.server.dao.user.UserRelationRepository;
import com.facetuis.server.dao.user.UserRepository;
import com.facetuis.server.model.order.OrderCommision;
import com.facetuis.server.model.order.OrderStatus;
import com.facetuis.server.model.user.User;
import com.facetuis.server.model.user.UserRelation;
import com.facetuis.server.service.pinduoduo.UserCommisionService;
import com.facetuis.server.service.pinduoduo.response.OrderDetail;
import com.facetuis.server.utils.CommisionUtils;
import com.facetuis.server.utils.CommisionUser;
import com.facetuis.server.utils.SysFinalValue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * 佣金增加计算
 */
@Component
public class CommisionCreateStrategy implements CommisionStrategy {

    @Autowired
    private OrderCommisionRepository orderCommisionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRelationRepository userRelationRepository;
    @Autowired
    private UserCommisionService userCommisionService;

    @Override
    public OrderCommision doCompute(final OrderDetail orderDetail) {
        OrderCommision orderCommision = orderCommisionRepository.findByOrderSn(orderDetail.getOrderSn());
        if(orderCommision == null){
            orderCommision = new OrderCommision();
            orderCommision.setUuid(UUID.randomUUID().toString());
            BeanUtils.copyProperties(orderDetail,orderCommision);
            orderCommision.setOrderStatus(orderDetail.getOrderStatus());
        }else{
            // 已经计算过，不再计算
            return orderCommision;
        }
        long promotionAmount = orderDetail.getPromotionAmount() * 1000;
        // 如果计算完成
        if(!orderCommision.getCompute()){
            //orderDetail.get
            StringBuilder levleRelation = new  StringBuilder();
            // 计算佣金
            String userLevel1Id = "";
            String userLevel2Id = "";
            String userLevel3Id = "";
            // 购买人
            User user = userRepository.findByPid(orderDetail.getpId());
            if(user == null){
                return null;
            }
            levleRelation.append(user.getLevel().getLevel());
            if(SysFinalValue.SYS_USER_ID.equals(user.getLevelUserId()) ){
               // 购买人无上级
                levleRelation.append("0");
                levleRelation.append("0");
                levleRelation.append("0");
            }else {
                UserRelation userRelation = userRelationRepository.findByUserId(user.getUuid());
                // 购买人 3个上级
                userLevel1Id = userRelation.getUserLevel1Id();
                levleRelation.append(getLevel(userLevel1Id));
                userLevel2Id = userRelation.getUserLevel2Id();
                levleRelation.append(getLevel(userLevel2Id));
                userLevel3Id = userRelation.getUserLevel3Id();
                levleRelation.append(getLevel(userLevel3Id));
            }
            orderCommision.setUserId(user.getUuid());
            orderCommision.setUser1Id(userLevel1Id);
            orderCommision.setUser2Id(userLevel2Id);
            orderCommision.setUser3Id(userLevel3Id);
            CommisionUser rate = CommisionUtils.getRate(levleRelation.toString());
            orderCommision.setUserCommision(CommisionUtils.multiply(promotionAmount,rate.getUserRate(),0) / 1000);

            if(!StringUtils.isEmpty(userLevel1Id) && !SysFinalValue.SYS_USER_ID.equals(userLevel1Id) ) {
                orderCommision.setUser1Commision(CommisionUtils.multiply(promotionAmount, rate.getUser1Rate(), 0) / 1000);
            }else{
                orderCommision.setUser1Commision(0L);
            }
            if(!StringUtils.isEmpty(userLevel2Id) && !SysFinalValue.SYS_USER_ID.equals(userLevel2Id) ) {
                orderCommision.setUser2Commision(CommisionUtils.multiply(promotionAmount, rate.getUser2Rate(), 0) / 1000);
            }else {
                orderCommision.setUser2Commision(0L);
            }
            if(!StringUtils.isEmpty(userLevel3Id) && !SysFinalValue.SYS_USER_ID.equals(userLevel3Id) ) {
                orderCommision.setUser3Commision(CommisionUtils.multiply(promotionAmount, rate.getUser3Rate(), 0) / 1000);
            }else{
                orderCommision.setUser3Commision(0L);
            }
            orderCommision.setFinish(false); // 未完成结算
            orderCommision.setCompute(false);// 未完成计算
            orderCommision.setFailFinish(false); // 未计算审核失败
        }
        // 计算个人用户佣金
        userCommisionService.computUserCommision(OrderStatus.getStatus(orderDetail.getOrderStatus()),orderCommision );
        return orderCommision;
    }


    public static void main(String[] args) {
        CommisionUser rate = CommisionUtils.getRate("0000");
        System.out.println(CommisionUtils.multiply(15L,rate.getUserRate(),0) / 1000);
    }

    public int getLevel(String userId){
        if(!StringUtils.isEmpty(userId)) {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                return optionalUser.get().getLevel().getLevel();
            }
        }
        return 0;
    }


}
