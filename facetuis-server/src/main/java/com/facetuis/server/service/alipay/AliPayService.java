package com.facetuis.server.service.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.facetuis.server.PayUtils;
import com.facetuis.server.app.web.request.AlipayNotifyRequest;
import com.facetuis.server.model.pay.PayStatus;
import com.facetuis.server.model.pay.PayType;
import com.facetuis.server.model.pay.Payment;
import com.facetuis.server.service.basic.BaseResult;
import com.facetuis.server.service.basic.BasicService;
import com.facetuis.server.service.payment.PaymentService;
import com.facetuis.server.utils.CommisionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.logging.Logger;

import static com.alipay.api.AlipayConstants.CHARSET_UTF8;

@Service
public class AliPayService extends BasicService {

    private static Logger logger = Logger.getLogger(AliPayService.class.getName());

    @Autowired
    private PaymentService paymentService;

    @Value("${alipay.app.id}")
    private String APP_ID;
    @Value("${alipay.app.private}")
    private String APP_PRIVATE_KEY;
    @Value("${alipay.server.url}")
    private String serverUrl;
    private String format = "json";
    @Value("${alipay.public}")
    private String ALIPAY_PUBLIC_KEY;
    @Value("${alipay.notify.url}")
    private String notfiyUrl;

    /**
     * 生成订单信息签名字符串
     * @param total_amount
     * @param subject
     * @param body
     * @return
     */
    public BaseResult<String> generateOrder(String total_amount,String subject,String body,String userId,String productId) {
        // 保存支付信息到数据库  subject==== 商品title
        //1.订单号 商品标题 商品价格 支付相关
        //生成订单号
       String tradeNo = PayUtils.getTradeNo();
        paymentService.save(tradeNo,subject,total_amount, PayStatus.WAIT_PAY, PayType.ALIPAY,userId,productId);
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(serverUrl, APP_ID, APP_PRIVATE_KEY, format, CHARSET_UTF8, ALIPAY_PUBLIC_KEY, "RSA2");
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody("member");
        model.setSubject(subject);
        model.setOutTradeNo(tradeNo);//订单号
        model.setTimeoutExpress("30m");
        double v = Double.parseDouble(total_amount);
        Double divide = CommisionUtils.divide(v, 100.00, 2);
        model.setTotalAmount(divide + "");
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(getServerUrl(notfiyUrl));
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            return new BaseResult(response.getBody());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return new BaseResult();
    }


    public BaseResult checkNotify(Map<String,String> map, AlipayNotifyRequest alipayNotifyRequest) {
        logger.info("AliPay callback sign verified::  | status :: " + alipayNotifyRequest.getTrade_status() + " | " + alipayNotifyRequest.getOut_trade_no());
        if (alipayNotifyRequest.getTrade_status().equals("TRADE_SUCCESS")) {
            // 支付单号
            String out_trade_no = alipayNotifyRequest.getOut_trade_no();
            Payment payment = paymentService.findByOutTradeNo(out_trade_no);
            if (payment != null) {
                if (alipayNotifyRequest.getApp_id().equals(APP_ID)) {
                    payment.setPayStatus(PayStatus.PAY_SUCCESS);
                    payment.setTradeNo(alipayNotifyRequest.getTrade_no());
                    paymentService.updatePayment(payment);
                }
            }
        }
        return new BaseResult(600, "未找到支付信息");
    }



}
