package com.facetuis.server.app.web;

import com.alipay.api.AlipayClient;
import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import com.facetuis.server.app.web.request.LoginRequest;
import com.facetuis.server.model.mobile.SmsModelCode;
import com.facetuis.server.model.user.User;
import com.facetuis.server.service.basic.BaseResult;
import com.facetuis.server.service.sms.SmsService;
import com.facetuis.server.service.user.UserService;
import com.facetuis.server.utils.CaptchafcUtil;
import com.facetuis.server.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

import static com.facetuis.server.utils.SysFinalValue.SESSION_CAPTCHA_ID;

@RestController
@RequestMapping("/1.0/login")
public class LoginController extends FacetuisController {


    private static final  Logger logger = Logger.getLogger(LoginController.class.getName());

    @Autowired
    private UserService userService;
    @Autowired
    private SmsService smsService;

    @RequestMapping(method = RequestMethod.POST)
    public BaseResponse login(@RequestBody LoginRequest request) {
        logger.info( "invite code :: " + request.getInvite_code());
        logger.info( "getHead_image :: " + request.getHead_image());
        logger.info( "getNick_name  :: " + request.getNick_name());
        logger.info( "getAccess_token :: " + request.getAccess_token());

        User mobileUser = null;
        User wechatUser = null;
        // 根据手机号获取用户
        if (!StringUtils.isEmpty(request.getMobile_number())) {
            mobileUser = userService.findByMobile(request.getMobile_number());
        }
        // 根据openid获取用户
        if (!StringUtils.isEmpty(request.getUnionid())) {
            wechatUser = userService.findByUnionid(request.getUnionid());
        }

        // 注册 - 无邀请码
        if(mobileUser == null && wechatUser == null && !StringUtils.isEmpty(request.getMobile_number()) && !StringUtils.isEmpty(request.getUnionid()) ){
            if (!StringUtils.isEmpty(request.getVerification_code())) {
                BaseResult baseResult = smsService.checkCode(request.getMobile_number(), request.getVerification_code(), SmsModelCode.LOGIN, false);
                if (baseResult.hasError()) {
                    return onResult(baseResult);
                }
                BaseResult<User> userBaseResult = userService.registerMobile(wechatUser, request.getMobile_number(), request.getInvite_code());
                mobileUser = userBaseResult.getResult();
                userBaseResult = userService.registerWechat(
                        mobileUser,
                        request.getOpenid(),
                        request.getAccess_token(),
                        request.getInvite_code(),
                        request.getNick_name(),
                        request.getHead_image(),
                        request.getUnionid()
                );
                User result = userBaseResult.getResult();
                result.setRecommandCode(result.getDisplayRecommend());
                return successResult(userBaseResult.getResult());
            }else{
                return new BaseResponse(400, "请填写手机验证码");
            }
        }

        // 登录
        if (mobileUser != null && wechatUser != null) {
            if (mobileUser.getUuid().equals(wechatUser.getUuid())) {
                User user = userService.login(mobileUser.getUuid());
                if(StringUtils.isEmpty(request.getInvite_code())){
                    return new BaseResponse(400, "登录失败，用户无邀请码");
                }
                // 更新access_token
                if(!user.getAccessToken().equals(request.getAccess_token())){
                    user.setAccessToken(request.getAccess_token());
                    userService.save(user);
                }
                //更新头像
                if( !StringUtils.isEmpty(request.getHead_image())){
                    user.setHeadImg(request.getHead_image());
                    userService.save(user);
                }
                // 更新昵称
                if( !StringUtils.isEmpty(request.getNick_name())){
                    user.setNickName(request.getNick_name());
                    userService.save(user);
                }
                // 第一次登录保存邀请码
                if(!StringUtils.isEmpty(request.getInvite_code()) && StringUtils.isEmpty(user.getInviteCode())){
                    BaseResult<User> user1 = userService.getUser(user, request.getInvite_code());
                    if(user1.hasError()){
                        return onResult(user1);
                    }
                    userService.createUser(user1.getResult());
                }
                user.setRecommandCode(user.getDisplayRecommend());
                return successResult(user);
            } else {
                return new BaseResponse(600, "手机用户和微信用户不匹配，登录失败");
            }
        }
        return new BaseResponse(400,"缺少请求参数");
    }


    @RequestMapping(value = "/mobile",method = RequestMethod.GET)
    public BaseResponse getMobile(String mobile,String code){
        if(StringUtils.isEmpty(mobile)){
           return setErrorResult(400,"缺少手机号码");
        }
        if(StringUtils.isEmpty(code)){
            return setErrorResult(400,"缺少手机验证码");
        }
        BaseResult baseResult = smsService.checkCode(mobile, code, SmsModelCode.LOGIN, true);
        if(baseResult.hasError()){
            return onResult(baseResult);
        }
        User mobileUser = userService.findByMobile(mobile);
        if(mobileUser != null) {
            mobileUser.setRecommandCode(mobileUser.getDisplayRecommend());
        }
        return successResult(mobileUser);
    }


    @RequestMapping(value = "/wechat",method = RequestMethod.GET)
    public BaseResponse getWechat(String unionid){
        if(StringUtils.isEmpty(unionid)){
            return setErrorResult(400,"缺少微信Unionid");
        }
        User user = userService.findByUnionid(unionid);
        if( user != null) {
            user.setRecommandCode(user.getDisplayRecommend());
        }
        return successResult(user);
    }

    @RequestMapping(value = "/captcha",method = RequestMethod.GET)
    public void getCaptcha(HttpServletRequest request, String mobile, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String s = RandomUtils.randomNumber(4);
        session.setAttribute(SESSION_CAPTCHA_ID,s);
        byte[] image = CaptchafcUtil.getImage(s);
        response.setContentType("image/png");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(image);
        outputStream.flush();
        outputStream.close();
    }
}
