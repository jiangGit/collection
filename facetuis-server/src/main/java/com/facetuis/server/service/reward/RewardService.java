package com.facetuis.server.service.reward;

import com.facetuis.server.dao.reward.RewardRepository;
import com.facetuis.server.model.reward.Reward;
import com.facetuis.server.service.basic.BasicService;
import com.facetuis.server.service.reward.vo.RewardInvitingVO;
import com.facetuis.server.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class RewardService extends BasicService {


    @Autowired
    private RewardRepository rewardRepository;

    public void create(Reward reward){
        rewardRepository.save(reward);
    }

    public Long getTodayByInviting(String userId){
        return rewardRepository.invitingToday(userId);
    }

    public Long getYesterdayByInviting (String userId){
        return rewardRepository.invitingYesterday(userId);
    }

    public Long getTimerInviting(String userId, Date start,Date end){
        return rewardRepository.invitingMonth(userId,start,end);
    }

    public RewardInvitingVO getInvitingVO(String userId){
        RewardInvitingVO vo = new RewardInvitingVO();
        String monthFirstDay = TimeUtils.getMonthFirstDay();
        String monthLastDay = TimeUtils.getMonthLastDay();
        Date startDate = TimeUtils.stringToDate(monthFirstDay );
        Date endDate = TimeUtils.stringToDate(monthLastDay);
        Long todayByInviting = getTodayByInviting(userId);
        todayByInviting =  todayByInviting == null ? 0 : todayByInviting;
        Long yesterdayByInviting = getYesterdayByInviting(userId);
        yesterdayByInviting = yesterdayByInviting == null ? 0 : yesterdayByInviting;
        Long timerInviting = getTimerInviting(userId, startDate, endDate);
        timerInviting = timerInviting == null ? 0 : timerInviting;
        vo.setMonthAmount(timerInviting);
        vo.setTodayAmount(todayByInviting);
        vo.setYesterdayAmount(yesterdayByInviting);
        Long aLong = rewardRepository.invitingTotal(userId);
        vo.setTotalAmount(aLong);
        return vo;
    }




}
