package com.facetuis.server.dao.user;

import com.facetuis.server.model.user.UserRelation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface UserRelationRepository extends JpaRepository<UserRelation,String> {

    UserRelation findByUserId(String userId);

    @Query(value = "select * from t_users_relation where user_level1id = ?1 and to_days(create_time) = to_days(now())",nativeQuery = true)
    List<UserRelation> findByUserLevel1IdToday(String userId);
    @Query(value = "select * from t_users_relation where user_level2id = ?1 and to_days(create_time) = to_days(now())",nativeQuery = true)
    List<UserRelation> findByUserLevel2IdToday(String userId);
    @Query(value = "select * from t_users_relation where user_level3id = ?1 and to_days(create_time) = to_days(now())",nativeQuery = true)
    List<UserRelation> findByUserLevel3IdToday(String userId);

    @Query(value = "select * from t_users_relation where user_level1id = ?1 and TO_DAYS( NOW( ) ) - TO_DAYS( create_time) = 1",nativeQuery = true)
    List<UserRelation> findByUserLevel1IdYesterday(String userId);
    @Query(value = "select * from t_users_relation where user_level2id = ?1 and TO_DAYS( NOW( ) ) - TO_DAYS( create_time) = 1",nativeQuery = true)
    List<UserRelation> findByUserLevel2IdYesterday(String userId);
    @Query(value = "select * from t_users_relation where user_level3id = ?1 and TO_DAYS( NOW( ) ) - TO_DAYS( create_time) = 1",nativeQuery = true)
    List<UserRelation> findByUserLevel3IdYesterday(String userId);

    List<UserRelation> findByUser1HighIdsLike(String userId);



}
