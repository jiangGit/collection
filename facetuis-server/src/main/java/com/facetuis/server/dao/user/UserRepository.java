package com.facetuis.server.dao.user;

import com.facetuis.server.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String>,JpaSpecificationExecutor<User> {

    public User findByMobileNumber(String mobileNumber);

    public User findByOpenid(String openId);

    List<User> findByNickNameLike(String nickName);
    List<User> findByNickName(String nickName);

    User findByRecommandCode(String recommandCode);

    User findByInviteCodeLike(String inviteCode);

    User findByRecommandCodeLike(String recommandCode);

    User findByToken(String token);

    User findByPid(String pid);

    List<User> findByInviteCode(String inviteCode);

    List<User> findByInviteCodeIn(List<String> inviteCode);

    User findByUnionId(String unionid);

    List<User> findByLevelUserId(String levelUserId);


    User findByTokenOrDeskAppToken(String token,String deskToken);


}
