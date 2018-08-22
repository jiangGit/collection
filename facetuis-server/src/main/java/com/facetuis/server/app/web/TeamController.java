package com.facetuis.server.app.web;

import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import com.facetuis.server.app.web.request.TeamOrdersSearchType;
import com.facetuis.server.app.web.request.TeamPeopleSearchType;
import com.facetuis.server.app.web.response.TeamOrderCountResponse;
import com.facetuis.server.app.web.response.TeamPeopleResponse;
import com.facetuis.server.app.web.response.TeamPopleCountResponse;
import com.facetuis.server.app.web.response.TeamUsersResponse;
import com.facetuis.server.model.order.Order;
import com.facetuis.server.model.user.User;
import com.facetuis.server.model.user.UserLevel;
import com.facetuis.server.model.user.UserRelation;
import com.facetuis.server.service.pinduoduo.OrderCommisionService;
import com.facetuis.server.service.pinduoduo.OrderService;
import com.facetuis.server.service.pinduoduo.response.OrderVO;
import com.facetuis.server.service.pinduoduo.response.TeamIncomVO;
import com.facetuis.server.service.reward.RewardService;
import com.facetuis.server.service.reward.vo.RewardInvitingVO;
import com.facetuis.server.service.user.UserRelationService;
import com.facetuis.server.service.user.UserService;
import com.facetuis.server.utils.NeedLogin;
import com.facetuis.server.utils.SMSUtils;
import com.facetuis.server.utils.TimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/1.0/team")
public class TeamController extends FacetuisController {

    @Autowired
    private UserRelationService userRelationService;

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderCommisionService orderCommisionService;
    @Autowired
    private RewardService rewardService;


    @RequestMapping(value = "/people/count",method = RequestMethod.GET)
    @NeedLogin(needLogin = true)
    public BaseResponse teamPeopleCount(){
        User user = getUser();
        UserRelation relation = userRelationService.getRelation(user.getUuid());
        // 总人数
        int total = userRelationService.getTotal(user,relation);
        // 直属高级
        Integer totalHigh = relation != null? relation.getUser1HighTotal() :0;
        totalHigh = totalHigh == null ? 0 : totalHigh;
        // 今日人数
        List<UserRelation> userRelationsToday = userRelationService.countPeopleToday(user.getUuid());
        // 昨天人数
        List<UserRelation> userRelationYesterday = userRelationService.countPeopleYesterday(user.getUuid());
        Integer people_directly = relation != null ? relation.getUser1Total() : 0;
        Integer people_directly_next =  relation != null ? relation.getUser2Total() + relation.getUser3Total() : 0;

        TeamPopleCountResponse teamPopleCountResponse = new TeamPopleCountResponse();
        teamPopleCountResponse.setPeople_directly(people_directly);
        teamPopleCountResponse.setPeople_total(total);
        teamPopleCountResponse.setPeople_directly_next(people_directly_next);
        teamPopleCountResponse.setPeople_yesterday(userRelationYesterday.size());
        teamPopleCountResponse.setPeople_today(userRelationsToday.size());
        teamPopleCountResponse.setPeople_directly_super(totalHigh);
        return successResult(teamPopleCountResponse);
    }


    /**
     * 指定时间团队人数列表
     * @return
     */
    @RequestMapping(value = "/people/count/{type}",method = RequestMethod.GET)
    @NeedLogin(needLogin = true)
    public BaseResponse teamPeopleCountToday(@PathVariable TeamPeopleSearchType type){
        User user = getUser();
        List<UserRelation> userRelations = new ArrayList<>();
        List<String> userIds = new ArrayList<>();
        if(type == TeamPeopleSearchType.TODAY) {
            userRelations = userRelationService.countPeopleToday(user.getUuid());
        }
        if(type == TeamPeopleSearchType.YESTERDAY){
            userRelations = userRelationService.countPeopleYesterday(user.getUuid());
        }
        if(type == TeamPeopleSearchType.LEVEL1){
            UserRelation relation = userRelationService.getRelation(user.getUuid());
            if(relation != null) {
                String user1HighIds = relation.getUser1Ids();
                if (!StringUtils.isEmpty(user1HighIds)) {
                    String[] split = user1HighIds.split(",");
                    if (split.length > 0) {
                        userIds = Arrays.asList(split);
                    }
                }
            }
        }
        if(type == TeamPeopleSearchType.LEVEL2){
            UserRelation relation = userRelationService.getRelation(user.getUuid());
            if(relation != null) {
                String user1HighIds = relation.getUser2Ids();
                if (!StringUtils.isEmpty(user1HighIds)) {
                    String[] split = user1HighIds.split(",");
                    if (split.length > 0) {
                        userIds = Arrays.asList(split);
                    }
                }
            }
        }
        for(UserRelation userRelation : userRelations){
            userIds.add(userRelation.getUserId());
        }
        TeamPeopleResponse result = new TeamPeopleResponse();
        List<TeamUsersResponse> responses = new ArrayList<>();
        if(userIds.size() > 0) {
            List<User> users = userService.findByIds(userIds);
            getTeamUser(users, responses,null);
        }
        result.setList(responses);
        result.setTotal(responses.size());
        return successResult(result);
    }



    @RequestMapping(value = "/orders/count",method = RequestMethod.GET)
    @NeedLogin(needLogin = true)
    public BaseResponse getTeamOrdersCount(){
        TeamOrderCountResponse response = new TeamOrderCountResponse();
        PageRequest pageable = PageRequest.of(0,1000);
        User user = getUser();
        String todayTime = TimeUtils.date2String(new Date());
        String yesterdayTime = TimeUtils.date2String(TimeUtils.getDateBefore(new Date(),1));
        // 当天订单
        Page<Order> todayOrders = orderService.findByDate(todayTime + " 00:00:00", todayTime + " 23:59:59", user.getUuid(), pageable);
        response.setOrder_today( (int)todayOrders.getTotalElements());
        // 所有订单
        Page<Order> teamOrders = orderService.findTeamOrders(user.getUuid(), pageable);
        response.setOrder_total( (int)teamOrders.getTotalElements());
        // 昨天订单
        Page<Order> yesterdayOrders = orderService.findByDate(yesterdayTime + " 00:00:00", yesterdayTime + " 23:59:59", user.getUuid(), pageable);
        response.setOrder_yesterday((int)yesterdayOrders.getTotalElements());
        //本月订单
        String monthFirst = TimeUtils.getMonthFirstDay();
        String monthLast = TimeUtils.getMonthLastDay();
        Page<Order> monthyOrders = orderService.findByDate(monthFirst + " 00:00:00", monthLast + " 23:59:59", user.getUuid(), pageable);
        response.setOrder_this_month((int)monthyOrders.getTotalElements());
        //上月订单
        String upperMonthFirst = TimeUtils.upperMonthFirst();
        String upperMonthLast = TimeUtils.upperMonthLast();
        Page<Order> upperMonthyOrders = orderService.findByDate(upperMonthFirst + " 00:00:00", upperMonthLast + " 23:59:59", user.getUuid(), pageable);
        response.setOrder_last_month((int)upperMonthyOrders.getTotalElements());
        return successResult(response);
    }

    @RequestMapping(value = "/orders/count/{type}",method = RequestMethod.GET)
    @NeedLogin(needLogin = true)
    public BaseResponse getTeamOrdersType(@PathVariable TeamOrdersSearchType type,Pageable pageable){
        User user = getUser();
        Page<Order> orders = null;
        if(type == TeamOrdersSearchType.TODAY){
            String todayTime = TimeUtils.date2String(new Date());
            orders = orderService.findByDate(todayTime + " 00:00:00", todayTime + " 23:59:59", user.getUuid(), pageable);
        }
        if(type == TeamOrdersSearchType.YESTERDAY){
            String yesterdayTime = TimeUtils.date2String(TimeUtils.getDateBefore(new Date(),1));
            orders = orderService.findByDate(yesterdayTime + " 00:00:00", yesterdayTime + " 23:59:59", user.getUuid(), pageable);
        }
        if(type == TeamOrdersSearchType.MONTH){
            String monthFirst = TimeUtils.getMonthFirstDay();
            String monthLast = TimeUtils.getMonthLastDay();
            orders = orderService.findByDate(monthFirst + " 00:00:00", monthLast + " 23:59:59", user.getUuid(), pageable);
        }
        if(type == TeamOrdersSearchType.PRE_MONTH){
            String upperMonthFirst = TimeUtils.upperMonthFirst();
            String upperMonthLast = TimeUtils.upperMonthLast();
            orders = orderService.findByDate(upperMonthFirst + " 00:00:00", upperMonthLast + " 23:59:59", user.getUuid(), pageable);
        }
        Page<OrderVO> orderVo = orderService.getOrderVo(orders.getContent());
        return successResult(orderVo);
    }




    @RequestMapping(value = "/orders/{status}",method = RequestMethod.GET)
    @NeedLogin(needLogin = true)
    public BaseResponse teamOrders(@PathVariable int status,String keywords,Pageable request){
        String nickName = "";
        String mobile = "";
        User user = getUser();
        if(!StringUtils.isEmpty(keywords)){
            if(SMSUtils.isChinaPhoneLegal(keywords)){
                mobile = keywords;
            }else {
                nickName = keywords;
            }
        }else{
            Page<OrderVO> statusOrders = orderService.findByStatus(user.getUuid(), status, request);
            return successResult(statusOrders);
        }
        Page<OrderVO> orders = orderService.findByMobileOrNickName(user.getUuid(), mobile, nickName, status, request);
        return successResult(orders);
    }

    @RequestMapping(value = "/people",method = RequestMethod.GET)
    @NeedLogin(needLogin = true)
    public BaseResponse teamPeople(String keywords){
        String nickName = "";
        String mobile = "";
        User user = getUser();
        List<User> users = null;
        if(!StringUtils.isEmpty(keywords)){
            if(SMSUtils.isChinaPhoneLegal(keywords)){
                mobile = keywords;
            }else {
                nickName = keywords;
            }
            // 根据条件查询团队成员
            users = userService.findByNickNameOrMobile(nickName, mobile);
        }else{
            // 查询所有成员
            List<String> team = userRelationService.getTeam(user.getUuid());
            users = userService.findByIds(team);
        }
        List<TeamUsersResponse> responses = new ArrayList<>();
        if(user != null){
            for(User u : users){
                List<User> byInviteCode = userService.findByInviteCode(u.getRecommandCode());
                TeamUsersResponse response = new TeamUsersResponse();
                BeanUtils.copyProperties(u,response);
                response.setRecommandNumber(byInviteCode.size());
                responses.add(response);
            }
        }

        TeamPeopleResponse result = new TeamPeopleResponse();
        result.setList(responses);
        result.setTotal(responses.size());
        return successResult(result);
    }

    @RequestMapping(value = "/people/high",method = RequestMethod.GET)
    @NeedLogin(needLogin = true)
    public BaseResponse teamPeopleLeve2(String keywords){
        String nickName = "";
        String mobile = "";
        User user = getUser();
        List<User> users = null;
        if(!StringUtils.isEmpty(keywords)){
            if(SMSUtils.isChinaPhoneLegal(keywords)){
                mobile = keywords;
            }else {
                nickName = keywords;
            }
            // 根据条件查询团队成员
            users = userService.findByNickNameOrMobile(nickName, mobile);
        }else{
            // 查询所有成员
            users = userRelationService.findHighUser(user.getUuid());
        }
        List<TeamUsersResponse> responses = new ArrayList<>();
        if(user != null){
            getTeamUser(users, responses,UserLevel.LEVEL2);
        }
        TeamPeopleResponse result = new TeamPeopleResponse();
        result.setList(responses);
        result.setTotal(responses.size());
        return successResult(result);
    }

    private void getTeamUser(List<User> users, List<TeamUsersResponse> responses,UserLevel level) {
        for(User u : users){
            List<User> byInviteCode = userService.findByInviteCode(u.getRecommandCode());
            if(level == null || u.getLevel() == level) {
                TeamUsersResponse response = new TeamUsersResponse();
                BeanUtils.copyProperties(u, response);
                response.setRecommandNumber(byInviteCode.size());
                responses.add(response);
            }
        }
    }


    /**
     * 查询团队收入
     * @return
     */
    @RequestMapping(value = "/income/count")
    @NeedLogin(needLogin = true)
    public BaseResponse income(){
        User user = getUser();
        TeamIncomVO income = orderCommisionService.getIncome(user.getUuid());
        return successResult(income);
    }

    @Value("${sys.activiy.inviting.start}")
    private String invitingStart;
    @Value("${sys.activiy.inviting.end}")
    private String invitingEnd;


    /**
     * 统计邀请奖励
     * @return
     */
    @RequestMapping(value = "/inviting/count")
    @NeedLogin(needLogin = true)
    public BaseResponse inviting(){
        User user = getUser();
        RewardInvitingVO invitingVO = rewardService.getInvitingVO(user.getUuid());
        invitingVO.setInvitingStart(invitingStart.replace("00:00:00",""));
        invitingVO.setInvitingEnd(invitingEnd.replace("23:59:59",""));
        return successResult(invitingVO);
    }
}
