package com.facetuis.server.dao.reward;

import com.facetuis.server.model.reward.Reward;
import com.facetuis.server.model.reward.RewardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface RewardRepository extends JpaRepository<Reward,String> {

    final String typeInviting = RewardType.INVITING.name();


    /**
     * 今天邀请奖励金额
     * @param userId
     * @return
     */
    @Query(value = "select sum(amount) from t_reward where user_id = ?1  and to_days(create_time) = to_days(now()) and reward_type = 'INVITING'",nativeQuery = true)
    Long invitingToday(String userId);

    /**
     * 昨天邀请奖励金额
     * @param userId
     * @return
     */
    @Query(value = "select sum(amount) from t_reward where user_id = ?1  and TO_DAYS( NOW( ) ) - TO_DAYS( create_time) = 1 and reward_type = 'INVITING'",nativeQuery = true)
    Long invitingYesterday(String userId);


    /**
     * 指定时间段邀请奖励金额
     * @param userId
     * @param star
     * @param end
     * @return
     */
    @Query(value = "select sum(amount) from t_reward  where user_id = ?1  and create_time > ?2  and  create_time < ?3 and reward_type = 'INVITING'",nativeQuery = true)
    Long invitingMonth(String userId, Date star,Date end);

    /**
     * 邀请人数
     * @param userId
     * @return
     */
    @Query(value = "select sum(amount) from t_reward  where user_id = ?1 and reward_type = 'INVITING'",nativeQuery = true)
    Long invitingTotal(String userId);


}
