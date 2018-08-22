package com.facetuis.server.service.pinduoduo;

import com.facetuis.server.dao.order.OrderCommisionRepository;
import com.facetuis.server.dao.user.UserRepository;
import com.facetuis.server.model.order.OrderCommision;
import com.facetuis.server.model.order.OrderStatus;
import com.facetuis.server.service.basic.BasicService;
import com.facetuis.server.service.pinduoduo.commision.CommisionContext;
import com.facetuis.server.service.pinduoduo.commision.CommisionStrategy;
import com.facetuis.server.service.pinduoduo.response.OrderDetail;
import com.facetuis.server.service.pinduoduo.response.OrderListBody;
import com.facetuis.server.service.pinduoduo.response.OrderListResponse;
import com.facetuis.server.service.pinduoduo.response.TeamIncomVO;
import com.facetuis.server.utils.TimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderCommisionService extends BasicService {

    @Autowired
    private OrderCommisionRepository orderCommisionRepository;
    @Autowired
    private CommisionContext commisionContext;
    @Autowired
    private UserRepository userRepository;


    /**
     * 订单佣金计算
     * 遍历订单
     * @param response
     */
    public void compute(OrderListResponse response){
        if(response == null || response.getOrder_list_get_response() == null){
            return;
        }
        List<OrderDetail> order_list = response.getOrder_list_get_response().getOrder_list();
        for(OrderDetail orderDetail : order_list){
            CommisionStrategy strategy = commisionContext.getStrategy(orderDetail.getOrderStatus());
            if(strategy != null) {
                // 结算订单佣金
                OrderCommision orderCommision = strategy.doCompute(orderDetail);
                if(orderCommision != null) {
                    orderCommisionRepository.save(orderCommision);
                }
            }
        }
        return;
    }

    /**
     * 统计指定用户的团队收入数据
     * @param userId
     * @return
     */
    public TeamIncomVO getIncome(String userId){
        TeamIncomVO vo = new TeamIncomVO();
        //////////////////////////////////////////////////////////////////////////
        ////////////////////////////////总收入////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////
        Long begTime = 0l;// 有史以来的订单
        Long endTime = System.currentTimeMillis()/1000; // 订单结束时间
        // 3级收入
        Long teamUser3Total = orderCommisionRepository.sumUser3(userId, begTime, endTime);
        teamUser3Total = teamUser3Total == null ? 0 : teamUser3Total;
        // 2级收入
        Long teamUser2Total = orderCommisionRepository.sumUser2(userId, begTime, endTime);
        teamUser2Total = teamUser2Total == null ? 0 : teamUser2Total;
        // 1级收入
        Long teamUser1Total = orderCommisionRepository.sumUser1(userId, begTime, endTime);
        teamUser1Total = teamUser1Total == null ? 0 : teamUser1Total;
        vo.setIncome_total(teamUser1Total + teamUser2Total + teamUser3Total);


        ////////////////////////////////////////////////////////////////////////////
        //////////////////////////// 今天收入////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////
        String begStr = TimeUtils.date2String(new Date()) + " 00:00:00";
        String endStr = TimeUtils.date2String(new Date()) + " 23:59:59";
        begTime = TimeUtils.stringToDateTime(begStr).getTime() / 1000;
        endTime = TimeUtils.stringToDateTime(endStr).getTime() / 1000;
        // 3级收入
        Long teamUser3TotalToday = orderCommisionRepository.sumUser3(userId, begTime, endTime);
        teamUser3TotalToday = teamUser3TotalToday == null ? 0 :teamUser3TotalToday;
        // 2级收入
        Long teamUser2TotalToday = orderCommisionRepository.sumUser2(userId, begTime, endTime);
        teamUser2TotalToday = teamUser2TotalToday == null ? 0 :teamUser2TotalToday;
        // 1级收入
        Long teamUser1TotalToday = orderCommisionRepository.sumUser1(userId, begTime, endTime);
        teamUser1TotalToday = teamUser1TotalToday == null ? 0 :teamUser1TotalToday;
        vo.setIncome_today(teamUser1TotalToday + teamUser2TotalToday + teamUser3TotalToday);
        ////////////////////////////////////////////////////////////////////////////////
        ////////////////////////// 昨天收入//////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////
        begStr =  TimeUtils.date2String(TimeUtils.getDateBefore(new Date(),1)) + " 00:00:00";
        endStr =  TimeUtils.date2String(TimeUtils.getDateBefore(new Date(),1)) + " 23:59:59";
        begTime = TimeUtils.stringToDateTime(begStr).getTime() / 1000;
        endTime = TimeUtils.stringToDateTime(endStr).getTime() / 1000;
        // 3级收入
        Long teamUser3TotalYeartoday = orderCommisionRepository.sumUser3(userId, begTime, endTime);
        teamUser3TotalYeartoday = teamUser3TotalYeartoday == null ? 0 :teamUser3TotalYeartoday;
        // 2级收入
        Long teamUser2TotalYeartoday = orderCommisionRepository.sumUser2(userId, begTime, endTime);
        teamUser2TotalYeartoday = teamUser2TotalYeartoday == null ? 0 :teamUser2TotalYeartoday;
        // 1级收入
        Long teamUser1TotalYeartoday = orderCommisionRepository.sumUser1(userId, begTime, endTime);
        teamUser1TotalYeartoday = teamUser1TotalYeartoday == null ? 0 :teamUser1TotalYeartoday;
        vo.setIncome_yesterday(teamUser1TotalYeartoday + teamUser2TotalYeartoday + teamUser3TotalYeartoday);

        /////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////本月收入////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////
        begStr =  TimeUtils.getMonthFirstDay() + " 00:00:00";
        endStr =  TimeUtils.getMonthLastDay() + " 23:59:59";
        begTime = TimeUtils.stringToDateTime(begStr).getTime() / 1000;
        endTime = TimeUtils.stringToDateTime(endStr).getTime() / 1000;
        // 3级收入
        Long teamUser3TotalMonth = orderCommisionRepository.sumUser3(userId, begTime, endTime);
        teamUser3TotalMonth = teamUser3TotalMonth == null ? 0 : teamUser3TotalMonth;
        // 2级收入
        Long teamUser2TotalMonth = orderCommisionRepository.sumUser2(userId, begTime, endTime);
        teamUser2TotalMonth = teamUser2TotalMonth == null ? 0 : teamUser2TotalMonth;
        // 1级收入
        Long teamUser1TotalMonth = orderCommisionRepository.sumUser1(userId, begTime, endTime);
        teamUser1TotalMonth = teamUser1TotalMonth == null ? 0 : teamUser1TotalMonth;
        vo.setIncome_this_month(teamUser3TotalMonth + teamUser2TotalMonth + teamUser1TotalMonth);

        /////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////上月收入////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////////////////
        begStr =  TimeUtils.upperMonthFirst() + " 00:00:00";
        endStr =  TimeUtils.upperMonthFirst() + " 23:59:59";
        begTime = TimeUtils.stringToDateTime(begStr).getTime() / 1000;
        endTime = TimeUtils.stringToDateTime(endStr).getTime() / 1000;
        // 3级收入
        Long teamUser3TotalLastMonth = orderCommisionRepository.sumUser3(userId, begTime, endTime);
        teamUser3TotalLastMonth = teamUser3TotalLastMonth == null ? 0 : teamUser3TotalLastMonth;
        // 2级收入
        Long teamUser2TotalLastMonth = orderCommisionRepository.sumUser2(userId, begTime, endTime);
        teamUser2TotalLastMonth = teamUser2TotalLastMonth == null ? 0 : teamUser2TotalLastMonth;
        // 1级收入
        Long teamUser1TotalLastMonth = orderCommisionRepository.sumUser1(userId, begTime, endTime);
        teamUser1TotalLastMonth = teamUser1TotalLastMonth == null ? 0 : teamUser1TotalLastMonth;
        vo.setIncome_last_month(teamUser3TotalLastMonth + teamUser2TotalLastMonth + teamUser1TotalLastMonth);
        return vo;
    }



}
