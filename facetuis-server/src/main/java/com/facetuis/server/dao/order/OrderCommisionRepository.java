package com.facetuis.server.dao.order;

import com.facetuis.server.model.order.OrderCommision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderCommisionRepository extends JpaRepository<OrderCommision,String> {


    OrderCommision findByOrderSn(String orderSn);


    /**
     * 购买用户分佣总计
     * @param userId
     * @return
     */
    @Query(value = "select sum(user_commision) from t_order_commision where user_id = ?1 and  order_create_time > ?2  and  order_create_time < ?3  ",nativeQuery = true)
    Long findTeamUserTotal(String userId,Long begTime,Long endTime);

    /**
     * 指定用户所有3级分佣统计
     * @param userId
     * @return
     */
    @Query(value = "select sum(user_commision + user1commision + user2commision) from t_order_commision where user3id = ?1 and  order_create_time > ?2  and  order_create_time < ?3 ",nativeQuery = true)
    Long findTeamUser3Total(String userId,Long begTime,Long endTime);

    /**
     * 指定用户所有2级分佣统计
     * @param userId
     * @return
     */
    @Query(value = "select sum(user_commision + user1commision) from t_order_commision where user2id = ?1 and  order_create_time > ?2  and  order_create_time < ?3 ",nativeQuery = true)
    Long findTeamUser2Total(String userId,Long begTime,Long endTime);

    /**
     * 指定用户所有1级分佣统计
     * @param userId
     * @return
     */
    @Query(value = "select sum(user_commision) from t_order_commision where user1id = ?1 and  order_create_time > ?2  and  order_create_time < ?3 ",nativeQuery = true)
    Long findTeamUser1Total(String userId,Long begTime,Long endTime);



    /**
     * 购买用户分佣总计
     * @param userId
     * @return
     */
    @Query(value = "select sum(user_commision) from t_order_commision where user_id = ?1 and order_status = ?2 and  order_create_time > ?2  and  order_create_time < ?3 and is_finish = ?4",nativeQuery = true)
    Long findTeamUserTotalByStatus(String userId,Long status,Long begTime,Long endTime,boolean isFinish );

    /**
     * 指定用户所有3级分佣统计
     * @param userId
     * @return
     */
    @Query(value = "select sum(user_commision + user1commision + user2commision) from t_order_commision where user3id = ?1 and order_status = ?2 and  order_create_time > ?3  and  order_create_time < ?4 and is_finish = ?4 ",nativeQuery = true)
    Long findTeamUser3TotalByStatus(String userId,Long status,Long begTime,Long endTime,boolean isFinish );

    /**
     * 指定用户所有2级分佣统计
     * @param userId
     * @return
     */
    @Query(value = "select sum(user_commision + user1commision) from t_order_commision where user2id = ?1  and order_status = ?2  and  order_create_time > ?3  and  order_create_time < ?4 and is_finish = ?4 ",nativeQuery = true)
    Long findTeamUser2TotalByStatus(String userId,Long status,Long begTime,Long endTime,boolean isFinish );

    /**
     * 指定用户所有1级分佣统计
     * @param userId
     * @return
     */
    @Query(value = "select sum(user_commision) from t_order_commision where user1id = ?1  and order_status = ?2  and  order_create_time > ?3  and  order_create_time < ?4 and is_finish = ?4",nativeQuery = true)
    Long findTeamUser1TotalByStatus(String userId ,Long status,Long begTime,Long endTime,boolean isFinish );

    @Query(value = "select sum( user1commision) from t_order_commision where user1id = ?1 and  order_create_time > ?2  and  order_create_time < ?3 ",nativeQuery = true)
    Long sumUser1(String userId,Long status,Long begTime);
    @Query(value = "select sum( user2commision) from t_order_commision where user2id = ?1 and  order_create_time > ?2  and  order_create_time < ?3 ",nativeQuery = true)
    Long sumUser2(String userId,Long status,Long begTime);
    @Query(value = "select sum( user3commision) from t_order_commision where user3id = ?1 and  order_create_time > ?2  and  order_create_time < ?3 ",nativeQuery = true)
    Long sumUser3(String userId,Long status,Long begTime);

}
