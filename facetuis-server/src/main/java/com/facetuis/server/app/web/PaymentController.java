package com.facetuis.server.app.web;

import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import com.facetuis.server.model.pay.Payment;
import com.facetuis.server.model.user.User;
import com.facetuis.server.service.payment.PaymentService;
import com.facetuis.server.utils.NeedLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/1.0/payment")
@RestController
public class PaymentController extends FacetuisController {

    @Autowired
    private PaymentService paymentService;

    /**
     * 查询当前登陆用户指定的支付信息
     * @param tradeNo
     * @return
     */
    @RequestMapping(value = "/trade/{tradeNo}",method = RequestMethod.GET)
    @NeedLogin(needLogin = true)
    public BaseResponse findByTradeNo(@PathVariable String tradeNo){
        User user = getUser();
        Payment payment = paymentService.findByTradeNo(tradeNo);
        if(payment != null && payment.getUserId().equals(user.getUuid())){
            return successResult(payment);
        }
        return setErrorResult(600,"未查到相关支付信息");
    }


}
