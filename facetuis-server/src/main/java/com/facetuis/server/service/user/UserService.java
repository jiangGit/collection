package com.facetuis.server.service.user;

import com.facetuis.server.dao.user.UserRepository;
import com.facetuis.server.model.pay.Payment;
import com.facetuis.server.model.product.Product;
import com.facetuis.server.model.user.User;
import com.facetuis.server.model.user.UserLevel;
import com.facetuis.server.model.user.UserRelation;
import com.facetuis.server.service.basic.BaseResult;
import com.facetuis.server.service.basic.BasicService;
import com.facetuis.server.service.payment.PaymentService;
import com.facetuis.server.service.pinduoduo.PinDuoDuoService;
import com.facetuis.server.service.pinduoduo.UserCommisionService;
import com.facetuis.server.service.user.utils.UserUtils;
import com.facetuis.server.service.wechat.WechatService;
import com.facetuis.server.service.wechat.response.MpGetUserInfoResponse;
import com.facetuis.server.utils.ProductUtils;
import com.facetuis.server.utils.RandomUtils;
import com.facetuis.server.utils.SysFinalValue;
import com.facetuis.server.utils.TimeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.*;
import java.util.logging.Logger;

@Service
public class UserService extends BasicService {

    private static Logger logger = Logger.getLogger(UserService.class.getName());

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PinDuoDuoService pinDuoDuoService;
    @Autowired
    private UserRelationService userRelationService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private WechatService wechatService;
    @Autowired
    private UserCommisionService userCommisionService;


    @Value("${sys.invite.code}")
    private String sysInviteCode;
    @Value("${sys.recommend.url}")
    private String recommendUrl;


    public User findRecommander(String userId){
        User user = userRepository.findById(userId).get();
        if(user == null || user.getLevelUserId() == null|| user.getLevelUserId().equals(SysFinalValue.SYS_USER_ID)){
            return null;
        }else{
            return userRepository.findById(user.getLevelUserId()).get();
        }
    }


    public User findByUnionid(String unionid){
        User user = userRepository.findByUnionId(unionid);
        if(user != null) {
            user.setRecommendUrl(String.format(recommendUrl, user.getDisplayRecommend()));
        }
        return user;
    }

    public User findByMobile(String mobile){
        User user = userRepository.findByMobileNumber(mobile);
        if(user == null){
            return null;
        }
        if(StringUtils.isEmpty(user.getDeskAppToken())){
            user.setDeskAppToken(RandomUtils.random(80));
        }
        userRepository.save(user);
        user.setRecommendUrl(String.format(recommendUrl,user.getDisplayRecommend()));
        return user;
    }

    public User findByOpenId(String openid){
        User user = userRepository.findByOpenid(openid);
        if(user != null) {
            user.setRecommendUrl(String.format(recommendUrl, user.getDisplayRecommend()));
        }
        return user;
    }

    public User login(String userId){
        User user = userRepository.findById(userId).get();
        user.setLoginTime(new Date());
        user.setToken(RandomUtils.random(64));
        String recommandCode = user.getRecommandCode();
        if(recommandCode != null) {
            String[] split = recommandCode.split(",");
            recommandCode = split[split.length - 1];
        }
        user.setRecommendUrl(String.format(recommendUrl,recommandCode));
        return userRepository.save(user);
    }


    /**
     * 注册手机用户
     * @param user
     * @param mobile
     * @param inviteCode
     * @return
     */
    public  BaseResult<User> registerMobile(User user, String mobile,String inviteCode) {
        BaseResult<User> userReslt = getUser(user,inviteCode);
        if(userReslt.hasError()){
            return userReslt;
        }
        user = userReslt.getResult();
        user.setMobileNumber(mobile);
        createUser(user);
        return new BaseResult<>(user);
    }

    /**
     * 通过网页注册微信用户
     * @param user
     * @param openid
     * @param accessToken
     * @return
     */
    public BaseResult registerWechatByWeb(User user,String openid,String accessToken){
        // 根据公众号信息获取用户信息
        logger.info("根据公众号信息获取用户信息 openid :: " + openid + " | accessToken :: " + accessToken);
        BaseResult<MpGetUserInfoResponse> userInfo = wechatService.getUserInfo(accessToken, openid);
        if(userInfo.hasError()){
           return userInfo;
        }
        MpGetUserInfoResponse userInfoResponse = userInfo.getResult();
        // 注册微信用户
        registerWechat(user,
                userInfoResponse.getOpenid(),
                accessToken,
                user.getInviteCode(),
                userInfoResponse.getNickname(),
                userInfoResponse.getHeadimgurl(),
                userInfoResponse.getUnionid());

        return new BaseResult();
    }

    /**
     * 注册微信用户
     * @param user
     * @param openid
     * @param accessToken
     * @param inviteCode
     * @return
     */
    public BaseResult<User> registerWechat(User user, String openid, String accessToken, String inviteCode,String nickName,String headImg,String unionid){
        BaseResult<User> userReslt = getUser(user,inviteCode);
        if(userReslt.hasError()){
            return userReslt;
        }
        user = userReslt.getResult();
        user.setOpenid(openid);
        user.setAccessToken(accessToken);
        user.setNickName(nickName);
        user.setHeadImg(headImg);
        user.setUnionId(unionid);
        createUser(user);
        return new BaseResult<>(user);
    }

    public void createUser(User user){
        userRepository.save(user);
        if(user.getEnable()){
            // 保存用户关系
            userRelationService.initUserRelation(user);
            // 计算邀请奖励
            userCommisionService.computInviting(user);
        }
    }

    public String  getRecommandCode(){
        String code = UserUtils.generateRecommandCode();
        User user = userRepository.findByRecommandCode(code);
        if(user == null){
            return code;
        }
        return  getRecommandCode();
    }


    public BaseResult<User> getUser(User user,String  inviteCode) {
        if(user == null){
            user = new User();
            user.setUuid(UUID.randomUUID().toString());
            user.setEnable(false);
            user.setInviteCode(inviteCode);
        }else{
            String inviteUserId = "";
            // 邀请码
            if(!StringUtils.isEmpty(inviteCode)) {
                if(inviteCode.length() < 5){
                    return new BaseResult<>(600,"邀请码格式错误");
                }
                if (inviteCode.equals(sysInviteCode)) {
                    inviteUserId = SysFinalValue.SYS_USER_ID;
                } else {
                    User tempUser = userRepository.findByRecommandCodeLike("%," + inviteCode + "%");
                    if (tempUser == null) {
                        return new BaseResult<>(600, "邀请码无效 - 01");
                    }
                    inviteUserId = tempUser.getUuid();
                }
                user.setEnable(true);
                user.setLevelUserId(inviteUserId);
                user.setInviteCode( inviteCode );
                if(StringUtils.isEmpty(user.getPid())){
                    // 设置推广位
                    String pid = pinDuoDuoService.createPid();
                    if (StringUtils.isEmpty(pid)) {
                        return new BaseResult<>(600, "推广位创建失败");
                    }
                    user.setPid(pid);
                }
                if(StringUtils.isEmpty(user.getRecommandCode())){
                    user.setRecommandCode("," + getRecommandCode() + ",");
                }
                if(StringUtils.isEmpty(user.getLevelTxt())){
                    user.setLevelTxt(getLevelTxt(user.getLevel()));
                }
                if(StringUtils.isEmpty(user.getToken())){
                    user.setToken(RandomUtils.random(64));
                }
            }else {
                // 设置推广位
                String pid = pinDuoDuoService.createPid();
                if (StringUtils.isEmpty(pid)) {
                    return new BaseResult<>(600, "推广位创建失败");
                }
                user.setPid(pid);
                user.setRecommandCode("," + getRecommandCode() + ",");
                user.setToken(RandomUtils.random(64));
                user.setLoginTime(new Date());
                user.setLevelTxt(getLevelTxt(user.getLevel()));
            }
        }
        return new BaseResult<>(user);
    }


    @Value("${user.level.no1.txt}")
    private String userLevelNo1Txt;
    @Value("${user.level.no2.txt}")
    private String userLevelNo2Txt;

    public String getLevelTxt(UserLevel level) {
        switch (level) {
            case LEVEL1:
                return userLevelNo1Txt;
            case LEVEL2:
                return userLevelNo2Txt;
        }
        return "";
    }


    public User getUserByToken(String token) {
        //return userRepository.findByToken(token);
        return userRepository.findByTokenOrDeskAppToken(token,token);
    }

    public List<User> findByIds(List<String> userIds){
        return userRepository.findAllById(userIds);
    }

    public List<User> findByNickNameOrMobile(String nickName,String mobile){
        List<User> users = new ArrayList<>();
        if(!StringUtils.isEmpty(nickName)){
            List<User> byNickName = userRepository.findByNickName(nickName);
            users.addAll(byNickName);
        }else if(!StringUtils.isEmpty(mobile)){
            User byMobileNumber = userRepository.findByMobileNumber(mobile);
            users.add(byMobileNumber);
        }
        return users;
    }

    public List<User> findByInviteCode(String inviteCode){
        String[] split = inviteCode.split(",");
        List<String> list = new ArrayList<>();
        for(String s : split){
            if(!StringUtils.isEmpty(s)) {
                list.add(s);
            }
        }
        return userRepository.findByInviteCodeIn(list);
    }

    /**
     * 用户升级
     * @return
     */
    public BaseResult upload(String userId,String tradeNo,String productId){
        // 查询用户交易状态
        Payment payment = paymentService.findByTradeNo(tradeNo);
        if(payment == null){
            return new BaseResult(600,"支付信息不存在");
        }
        if(!payment.getProductId().equals(productId)){
            return new BaseResult(600,"支付产品不存在");
        }
        if(!userId.equals(payment.getUserId())){
            return new BaseResult(600,"支付用户不匹配");
        }
        User user = userRepository.findById(userId).get();
        if(user != null){
            Product product = ProductUtils.getProduct(productId);
            UserRelation relation = userRelationService.getRelation(user.getUuid());
            Integer user1Total = relation.getUser1Total();
            if(!(user1Total < 20)){
                return new BaseResult(600,"升级要求未达到：直属会员 ≥ 20人");
            }
            if(!(relation.getUser2Total() + relation.getUser3Total() < 40)){
                return new BaseResult(600,"升级要求未达到：直属会员下级 ≤ 40人");
            }
            if(user.getLevel().equals(UserLevel.LEVEL1)){
                user.setLevel(UserLevel.LEVEL2);
                user.setLevelTxt(getLevelTxt(user.getLevel()));
                setExpireTime(user, product);
                // 将用户添加到上级用户的高级用户列表
                if(!SysFinalValue.SYS_USER_ID.equals(user.getLevelUserId())){
                    userRelationService.addHigher(user.getUuid(),user.getLevelUserId());
                }
            }else{
                setExpireTime(user, product);
            }
            userRepository.save(user);
        }
        return new BaseResult();
    }

    private void setExpireTime(User user, Product product) {
        if(user.getExpireTime() == null) {
            // 当期时间 + 产品时间
            Date date = TimeUtils.plusDay(product.getValues(), new Date());
            user.setExpireTime(date);
        }else {
            // 判断是否已过期
            if (TimeUtils.compare(user.getExpireTime()) <= 0) {
                Date date = TimeUtils.plusDay(product.getValues(), user.getExpireTime());
                user.setExpireTime(date);
            }else{
                Date date = TimeUtils.plusDay(product.getValues(), new Date());
                user.setExpireTime(date);
            }
        }
    }

    public void lowerLevel(User user){
        if(user == null){
            return;
        }
        user.setLevel(UserLevel.LEVEL1);
        user.setLevelTxt(getLevelTxt(UserLevel.LEVEL1));
        user.setExpireTime(null);
        save(user);
        // 更改用户关系
        userRelationService.removeHighter(user.getUuid());

    }

    public void save(User user){
        userRepository.save(user);
    }

    //按条件分页查询
    public Page<User> findBy(User user, int pageNumber, int pageSize) {
        Pageable pageable=new PageRequest(pageNumber, pageSize);  //分页信息
        Specification<User> spec = new Specification<User>() {        //查询条件构造
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<String> mobile_number = root.get("mobileNumber");
                Path<String> nick_name = root.get("nickName");
                Path<String> level_txt = root.get("levelTxt");
                Predicate p1 = cb.like(mobile_number, "%"+user.getMobileNumber()+"%");
                Predicate p2 = cb.like(nick_name, "%"+user.getNickName()+"%");
                Predicate p3 = cb.like(level_txt, "%"+user.getLevelTxt()+"%");
                Predicate p = cb.and(p1,p2,p3);
                return p;
            }
        };
        return userRepository.findAll(spec, pageable);
    }
    //获取一个
    public Optional<User> findById(String uuid) {
        return userRepository.findById(uuid);
    }



}
