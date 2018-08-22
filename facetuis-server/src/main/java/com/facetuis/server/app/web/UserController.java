package com.facetuis.server.app.web;

import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import com.facetuis.server.app.web.request.RegisterRequest;
import com.facetuis.server.app.web.request.UpgradedRequest;
import com.facetuis.server.app.web.request.UserSettingsRequest;
import com.facetuis.server.app.web.response.UserRecommanderResponse;
import com.facetuis.server.dao.user.UserRepository;
import com.facetuis.server.model.commision.CommisionSettings;
import com.facetuis.server.model.mobile.SmsModelCode;
import com.facetuis.server.model.product.Product;
import com.facetuis.server.model.user.User;
import com.facetuis.server.model.user.UserCommision;
import com.facetuis.server.model.user.UserLevel;
import com.facetuis.server.service.basic.BaseResult;
import com.facetuis.server.service.pinduoduo.UserCommisionService;
import com.facetuis.server.service.sms.SmsService;
import com.facetuis.server.service.user.UserService;
import com.facetuis.server.utils.NeedLogin;
import com.facetuis.server.utils.ProductUtils;
import com.facetuis.server.utils.SysFinalValue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.logging.Logger;

@RequestMapping("/1.0/user")
@Controller
public class UserController extends FacetuisController{

    private static final Logger logger  = Logger.getLogger(UserController.class.getName());

    @Autowired
    private UserService userService;
    @Value("${wechat.mp.code.url}")
    private String wechatMpCodeUrl;
    @Value("${wechat.app.id}")
    private String appid;
    @Value("${sys.server.host}")
    private String host;
    @Value("${sys.web.register.url}")
    private String webRegister;
    @Autowired
    private UserCommisionService userCommisionService;
    @Autowired
    private SmsService smsService;
    @Value("${sys.invite.code}")
    private String sysInviteCode;
    @Autowired
    private UserRepository userRepository;

    /**
     * 判断是否微信浏览器访问注册页面
     * 方法目的：跳转到网页注册页面
     * @param ic
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/enroll/{ic}",method = RequestMethod.GET)
    public void regPage(@PathVariable String ic,HttpServletRequest request,HttpServletResponse response) throws IOException {
        String userAgent = request.getHeader("user-agent").toLowerCase();
        String redir = "http://" + host + "/register/regdisger.html?ic=" + ic;
        redir = URLEncoder.encode(redir,"UTF-8");
        if(userAgent.indexOf("micromessenger")>-1){//微信客户端
            String location = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb48f855755bdeaff&redirect_uri=" + redir + "&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";
            //response.sendRedirect(location);
            //return  "redirect:" + location;
            response.setStatus(302);
            response.setHeader("location", location);
        }else{
            //response.sendRedirect(redir);
            response.setStatus(302);
            redir = URLDecoder.decode(redir,"UTF-8");
            response.setHeader("location", redir);
            //return  "redirect:" + redir;
        }
    }


    /**
     * 网页注册
     * @param registerRequest
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse register(@RequestBody RegisterRequest registerRequest,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return erroorResult(bindingResult);
        }
        BaseResult smsBaseResult = smsService.checkCode(registerRequest.getMobileNumber(), registerRequest.getCode(), SmsModelCode.REGISTER,false);
        if(smsBaseResult.hasError()){
            return onResult(smsBaseResult);
        }
        String inviteUserId = "";
        if(registerRequest.getInviteCode().equals(sysInviteCode)){
            inviteUserId = SysFinalValue.SYS_USER_ID;
        }else{
            User tempUser = userRepository.findByRecommandCodeLike( "%," + registerRequest.getInviteCode() + "%");
            if(tempUser == null){
                 return new BaseResponse(600,"邀请码无效");
            }
            inviteUserId = tempUser.getUuid();
        }
        User mobileUser = userService.findByMobile(registerRequest.getMobileNumber());
        if(mobileUser != null){
            return setErrorResult(600,"手机号已存在");
        }
        if(!StringUtils.isEmpty(registerRequest.getOpenid())){
            User unionidUser = userService.findByOpenId(registerRequest.getOpenid());
            if(unionidUser != null){
                return setErrorResult(600,"微信帐号已存在");
            }
        }
        BaseResult<User> userBaseResult = userService.registerMobile(mobileUser, registerRequest.getMobileNumber(), registerRequest.getInviteCode());
        if(userBaseResult.hasError()){
            return onResult(userBaseResult);
        }else{
            User user = userBaseResult.getResult();
            user.setInviteCode(registerRequest.getInviteCode());
            BaseResult baseResult = userService.registerWechatByWeb(user, registerRequest.getOpenid(), registerRequest.getAccessToken());
            if(baseResult.hasError()){
                return onResult(baseResult);
            }
        }
        return successResult();
    }

    @RequestMapping(value = "/setting",method = RequestMethod.POST)
    @NeedLogin(needLogin = true)
    @ResponseBody
    public BaseResponse setting(@RequestBody UserSettingsRequest userSettingsRequest){
        User user = getUser();
        user.setSettingRQInGoodsImage(userSettingsRequest.getSettingRQInGoodsImage());
        userService.save(user);
        userSettingsRequest.getSettingRQInGoodsImage();
        return successResult();
    }


    /**
     * 推广链接
     * @return
     */
    //@RequestMapping(value = "/enroll/{recommender}",method = RequestMethod.GET)
    public void enroll(@PathVariable String recommender, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String ua = request.getHeader("user-agent");
        if (ua.indexOf("micromessenger") > 0) {
            String url = "http://" + host + "/facetuis/1.0/wechat/mp/userinfo/" + recommender;
            url = URLEncoder.encode(url,"UTF-8");
            String location = String.format(wechatMpCodeUrl, appid, url,recommender);
            // 跳转到微信
            response.sendRedirect(location);
            return;
        }
        response.sendRedirect(webRegister);
    }


    /**
     * 获取推荐自己的用户
     * @return
     */
    @RequestMapping(value = "/recommender",method = RequestMethod.GET)
    @NeedLogin(needLogin = true)
    @ResponseBody
    public BaseResponse getCommander() {
        User user = getUser();
        UserRecommanderResponse response = new UserRecommanderResponse();
        User recommander = userService.findRecommander(user.getUuid());
        if(recommander == null){
            return successResult();
        }
        BeanUtils.copyProperties(recommander, response);
        return successResult(response);
    }

    /**
     * 用户/续费升级
     * @return
     */
    @RequestMapping(value = "/upgraded",method = RequestMethod.POST)
    @NeedLogin(needLogin = true)
    @ResponseBody
    public BaseResponse upgraded(@RequestBody UpgradedRequest request, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return setErrorResult(bindingResult,400,"请求参数错误");
        }
        User user = getUser();
        if(user.getLevel().equals(UserLevel.LEVEL2)){
           // return setErrorResult(600,"已经是最高级");
            logger.info("用户续费");
        }
        BaseResult upload = userService.upload(user.getUuid(), request.getOutTradeNo(), request.getProductId());
        if(!upload.hasError()){
            CommisionSettings commisionSettings = new CommisionSettings();
            Product product = ProductUtils.getProduct(request.getProductId());
            Long cash = 0l;
            if(product.getValues() == 30){
                cash = commisionSettings.getMonthAdd();
            }else{
                cash = commisionSettings.getYearAdd();
            }
            // 发送用户升级奖励
            userCommisionService.addInvitingCash(user.getUuid(),cash);
        }
        return onResult(upload);
    }




}
