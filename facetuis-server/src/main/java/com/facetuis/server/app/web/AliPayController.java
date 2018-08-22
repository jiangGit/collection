package com.facetuis.server.app.web;

import com.alipay.api.internal.util.AlipaySignature;
import com.facetuis.server.app.web.basic.BaseResponse;
import com.facetuis.server.app.web.basic.FacetuisController;
import com.facetuis.server.app.web.request.AlipayNotifyRequest;
import com.facetuis.server.model.product.Product;
import com.facetuis.server.model.user.User;
import com.facetuis.server.service.alipay.AliPayService;
import com.facetuis.server.service.basic.BaseResult;
import com.facetuis.server.utils.NeedLogin;
import com.facetuis.server.utils.ProductUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/1.0/alipay")
public class AliPayController extends FacetuisController {

    private static Logger logger = Logger.getLogger(AliPayController.class.getName());

    @Autowired
    private AliPayService aliPayService;

    /**
     * 生成APP手机端的签名后的订单信息字符串
     * @param productId
     * @return
     */
    @RequestMapping( value = "/order/info/{productId}",method = RequestMethod.GET)
    @NeedLogin(needLogin = true)
    public BaseResponse generateOrder(@PathVariable String productId){
        Product product = ProductUtils.getProduct(productId);
        if(product == null){
            return setErrorResult(400,"商品不存在");
        }
        User user = getUser();
        String userId = null;
        if(user != null){
            userId = user.getUuid();
        }
        String body = "用户：" + userId + "购买了" + product.getTitle();
        BaseResult stringBaseResult = aliPayService.generateOrder(product.getAmount(), product.getTitle(), body,userId,productId);
        return onResult(stringBaseResult);
    }

    @RequestMapping(value = "/notify",method = RequestMethod.POST)
    public String aliNotify(AlipayNotifyRequest alipayNotifyRequest){
        Map<String,String> map = new HashMap<>();
        map.put("notify_time",alipayNotifyRequest.getNotify_time());
        map.put("notify_type",alipayNotifyRequest.getNotify_type());
        map.put("notify_id",alipayNotifyRequest.getNotify_id());
        map.put("app_id",alipayNotifyRequest.getApp_id());
        map.put("charset",alipayNotifyRequest.getCharset());
        map.put("version",alipayNotifyRequest.getVersion());
        map.put("sign_type",alipayNotifyRequest.getSign_type());
        map.put("sign",alipayNotifyRequest.getSign());
        map.put("trade_no",alipayNotifyRequest.getTrade_no());
        map.put("out_trade_no",alipayNotifyRequest.getOut_trade_no());
        map.put("out_biz_no",alipayNotifyRequest.getOut_biz_no());
        map.put("buyer_id",alipayNotifyRequest.getBuyer_id());
        map.put("buyer_logon_id",alipayNotifyRequest.getBuyer_logon_id());
        map.put("seller_id",alipayNotifyRequest.getSeller_id());
        map.put("seller_email",alipayNotifyRequest.getSeller_email());
        map.put("trade_status",alipayNotifyRequest.getTrade_status());
        map.put("total_amount",alipayNotifyRequest.getTotal_amount());
        map.put("receipt_amount",alipayNotifyRequest.getReceipt_amount());
        map.put("invoice_amount",alipayNotifyRequest.getInvoice_amount());
        map.put("buyer_pay_amount",alipayNotifyRequest.getBuyer_pay_amount());
        map.put("point_amount",alipayNotifyRequest.getPoint_amount());
        map.put("refund_fee",alipayNotifyRequest.getRefund_fee());
        map.put("subject",alipayNotifyRequest.getSubject());
        map.put("body",alipayNotifyRequest.getBody());
        map.put("gmt_create",alipayNotifyRequest.getGmt_create());
        map.put("gmt_payment",alipayNotifyRequest.getGmt_payment());
        map.put("gmt_refund",alipayNotifyRequest.getGmt_refund());
        map.put("gmt_close",alipayNotifyRequest.getGmt_close());
        map.put("fund_bill_list",alipayNotifyRequest.getFund_bill_list());
        map.put("passback_params",alipayNotifyRequest.getPassback_params());
        map.put("voucher_detail_list",alipayNotifyRequest.getVoucher_detail_list());
        BaseResult baseResult = aliPayService.checkNotify(map,alipayNotifyRequest);
        if(baseResult.hasError()){
            return "ERROR";
        }
        return "SUCCESS";
    }

}
