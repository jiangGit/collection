package com.facetuis.server.app.web;

import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import com.facetuis.server.model.pay.WithdrawCashRequest;
import com.facetuis.server.model.user.User;
import com.facetuis.server.service.basic.BaseResult;
import com.facetuis.server.service.payment.WithdrawCashRequestService;
import com.facetuis.server.service.payment.vo.UserCashVO;
import com.facetuis.server.utils.NeedLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/1.0/cash")
public class CashController extends FacetuisController {

    @Autowired
    private WithdrawCashRequestService withdrawCashRequestService;

    @RequestMapping(method = RequestMethod.GET)
    @NeedLogin(needLogin = true)
    public BaseResponse index(){
        User user = getUser();
        String uuid = user.getUuid();
        UserCashVO cashInfo = withdrawCashRequestService.getCashInfo(uuid);
        return successResult(cashInfo);
    }

    @RequestMapping(method = RequestMethod.POST)
    @NeedLogin(needLogin = true)
    public BaseResponse cash(@RequestBody WithdrawCashRequest request, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return erroorResult(bindingResult);
        }
        if(request == null || request.getAmount() <= 0){
            return new BaseResponse(400,"提现金额必须大于0");
        }
        User user = getUser();
        String uuid = user.getUuid();
        request.setUserId(uuid);
        BaseResult baseResult = withdrawCashRequestService.create(request);
        return onResult(baseResult);
    }

}
