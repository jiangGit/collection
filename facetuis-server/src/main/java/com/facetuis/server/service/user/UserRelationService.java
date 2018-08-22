package com.facetuis.server.service.user;

import com.facetuis.server.dao.user.UserRelationRepository;
import com.facetuis.server.dao.user.UserRepository;
import com.facetuis.server.model.user.User;
import com.facetuis.server.model.user.UserLevel;
import com.facetuis.server.model.user.UserRelation;
import com.facetuis.server.service.basic.BaseResult;
import com.facetuis.server.service.basic.BasicService;
import com.facetuis.server.utils.SysFinalValue;
import com.facetuis.server.utils.TimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class UserRelationService extends BasicService {

    private static final Logger logger = Logger.getLogger(UserRelationService.class.getName());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRelationRepository userRelationRepository;

    /**
     * 新用户初始化关系
     * @param user
     */
    public void initUserRelation(User user){
        UserRelation userRelation = userRelationRepository.findByUserId(user.getUuid());
        if(userRelation == null){
            userRelation = new UserRelation();
            userRelation.setUuid(UUID.randomUUID().toString());
            userRelation.setCreateTime(new Date());
            userRelation.setUserId(user.getUuid());
            userRelation.setUserLevel1Id(user.getLevelUserId());// 上级ID
            if(!user.getLevelUserId().equals(SysFinalValue.SYS_USER_ID)){
                String user1 = user.getLevelUserId();
                String user2 = "";
                String user3 = "";
                // 查询 上级的上级
                // 上级
                User level1User = userRepository.findById(user.getLevelUserId()).get();
                if(level1User == null){
                    logger.info("1-####### 未找到用户上级，用户::" + user.getLevelUserId());
                }
                // 上级的上级ID
                user2 = level1User.getLevelUserId();
                if(!StringUtils.isEmpty(user2)) {
                    userRelation.setUserLevel2Id(user2);
                    if (!user2.equals(SysFinalValue.SYS_USER_ID)) {
                        // 上级的上级
                        User level3User = userRepository.findById(user2).get();
                        if (level3User == null) {
                            logger.info("2-####### 未找到用户上级，用户::" + user.getLevelUserId());
                        }
                        user3 = level3User.getLevelUserId();
                        userRelation.setUserLevel3Id(user3);
                    }
                }
                addRelation(user1,user2,user3,user.getUuid());
            }
            userRelationRepository.save(userRelation);
        }else{
            logger.info("######## 用户可能重复注册");
        }
    }

    /**
     * 更新用户关系
     * @param user1
     * @param user2
     * @param user3
     * @param user
     */
    public void addRelation(String user1 ,String user2,String user3,String user){
        // 更新上级 一级用户数据
        UserRelation userRelation = userRelationRepository.findByUserId(user1);
        if(userRelation == null){
            logger.info("上级用户关系未找到");
            return;
        }
        String user1Ids = userRelation.getUser1Ids();
        if(user1Ids == null){
            user1Ids = "";
        }
        user1Ids = user1Ids + "," + user;
        userRelation.setUser1Ids(user1Ids);
        int user1Total = userRelation.getUser1Total();
        user1Total = user1Total + 1;
        userRelation.setUser1Total(user1Total);
        userRelationRepository.save(userRelation);

        if(!StringUtils.isEmpty(user2)){
            UserRelation userRelation2 = userRelationRepository.findByUserId(user2);
            if(userRelation2 != null){
                String user2Ids = userRelation2.getUser2Ids();
                if(user2Ids == null){
                    user2Ids = "";
                }
                user2Ids = user2Ids + "," + user;
                userRelation2.setUser2Ids(user2Ids);
                int user2Total = userRelation2.getUser2Total();
                user2Total = user2Total + 1;
                userRelation2.setUser2Total(user2Total);
                userRelationRepository.save(userRelation2);
            }
        }
        if(!StringUtils.isEmpty(user3)){
            UserRelation userRelation3 = userRelationRepository.findByUserId(user3);
            if(userRelation3 != null){
                String user3Ids = userRelation3.getUser3Ids();
                if(user3Ids == null){
                    user3Ids = "";
                }
                user3Ids = user3Ids + "," + user;
                userRelation3.setUser3Ids(user3Ids);
                int user3Total = userRelation3.getUser3Total();
                user3Total = user3Total + 1;
                userRelation3.setUser3Total(user3Total);
                userRelationRepository.save(userRelation3);
            }
        }
    }

    public UserRelation getRelation(String userId){
        return userRelationRepository.findByUserId(userId);
    }
    /**
     * 获取总人数
     * @return
     */
    public int getTotal(User user,UserRelation relation){
        if(relation == null){
            return 0;
        }
        if(user.getLevel().equals(UserLevel.LEVEL1) && false){
            int total = 0;
            int user1Total = relation.getUser1Total();
            total = total + user1Total;
            String user1Ids = relation.getUser1Ids();
            if(StringUtils.isEmpty(user1Ids)){
               return  total;
            }
            String[] split = user1Ids.split(",");
            for(String u : split){
                Optional<User> user1 = userRepository.findById(u);
                 if(user1.isPresent()){
                     User value = user1.get();
                     if(value.getLevel().equals(UserLevel.LEVEL2)){
                         UserRelation userRelation = userRelationRepository.findByUserId(value.getUuid());
                         total = total + userRelation.getUser1Total();
                         total = total + userRelation.getUser2Total();
                     }
                 };
            }
            return total;
        }else {
            return relation.getUser1Total() + relation.getUser2Total() + relation.getUser3Total();
        }
    }

    /**
     * 获取团队所有成员
     * @param userId
     * @return
     */
    public List<String> getTeam(String userId){
        UserRelation userRelation = userRelationRepository.findByUserId(userId);
        if(userRelation == null){
            return Collections.EMPTY_LIST;
        }
        List<String> list = new ArrayList<>();
        String user1Ids = userRelation.getUser1Ids();
        if(!StringUtils.isEmpty(user1Ids)){
            String[] split = user1Ids.split(",");
            if(split.length > 0){
                list.addAll(Arrays.asList(split));
            }
        }
        String user2Ids = userRelation.getUser2Ids();
        if(!StringUtils.isEmpty(user2Ids)){
            String[] split = user2Ids.split(",");
            if(split.length > 0){
                list.addAll(Arrays.asList(split));
            }
        }
        String user3Ids = userRelation.getUser3Ids();
        if(!StringUtils.isEmpty(user3Ids)){
            String[] split = user3Ids.split(",");
            if(split.length > 0){
                list.addAll(Arrays.asList(split));
            }
        }
        return list;
    }

    public List<User> findHighUser(String userId){
        UserRelation byUserId = userRelationRepository.findByUserId(userId);
        String user1HighIds = byUserId.getUser1HighIds();
        if(StringUtils.isEmpty(user1HighIds)){
            return Collections.EMPTY_LIST;
        }
        String[] split = user1HighIds.split(",");
        List<String> ids = Arrays.asList(split);
        return userRepository.findAllById(ids);
    }

    /**
     * 统计今天团队人数
     * @param userId
     * @return
     */
    public List<UserRelation> countPeopleToday(String userId){
        List<UserRelation> userLevel1 = userRelationRepository.findByUserLevel1IdToday(userId);
        List<UserRelation> userLevel2 = userRelationRepository.findByUserLevel2IdToday(userId);
        List<UserRelation> userLevel3 = userRelationRepository.findByUserLevel3IdToday(userId);
        if(userLevel1 == null){
            userLevel1 = new ArrayList<>();
        }
        userLevel1.addAll(userLevel2);
        userLevel1.addAll(userLevel3);
        return userLevel1;
    }

    /**
     * 统计昨天团队人数
     * @param userId
     * @return
     */
    public List<UserRelation> countPeopleYesterday(String userId){
        List<UserRelation> userLevel1 = userRelationRepository.findByUserLevel1IdYesterday(userId);
        List<UserRelation> userLevel2 = userRelationRepository.findByUserLevel2IdYesterday(userId);
        List<UserRelation> userLevel3 = userRelationRepository.findByUserLevel3IdYesterday(userId);
        if(userLevel1 == null){
            userLevel1 = new ArrayList<>();
        }
        userLevel1.addAll(userLevel2);
        userLevel1.addAll(userLevel3);
        return userLevel1;
    }


    /**
     * 新增高级用户
     * @param userId 升级用户
     * @param higherId 升级用户的上级用户
     * @return
     */
    public void addHigher(String userId,String higherId){
        UserRelation userRelation = userRelationRepository.findByUserId(higherId);
        // 新增一级用户
//        String user1Ids = userRelation.getUser1Ids();
//        user1Ids = user1Ids == null ? "" : user1Ids;
//        user1Ids = user1Ids + "," + userId;
//        userRelation.setUser1Ids(user1Ids);
        String user1HighIds = userRelation.getUser1HighIds();
        user1HighIds = user1HighIds == null ? "" : user1HighIds;
        user1HighIds = user1HighIds + "," + userId;
        userRelation.setUser1HighIds(user1HighIds);
        // 统计数量 +1
        Integer user1HighTotal = userRelation.getUser1HighTotal();
        user1HighTotal = user1HighTotal + 1;
        userRelation.setUser1HighTotal(user1HighTotal);
        userRelationRepository.save(userRelation);
    }


    public void removeHighter(String userId){
        List<UserRelation> userRelations = userRelationRepository.findByUser1HighIdsLike("%" + userId + "%");
        for(UserRelation userRelation : userRelations){
            String user1HighIds = userRelation.getUser1HighIds();
            String replace = "";
            if(user1HighIds.indexOf("," + userId) >= 0){
                replace = user1HighIds.replace("," + userId, "");
            }else{
                replace = user1HighIds.replace(userId, "");
            }
            userRelation.setUser1HighIds(replace);
        }
        userRelationRepository.saveAll(userRelations);
    }
}
