package com.facetuis.server.service.wechat;

import com.facetuis.server.PayUtils;
import com.facetuis.server.model.pay.PayStatus;
import com.facetuis.server.model.pay.PayType;
import com.facetuis.server.model.pay.Payment;
import com.facetuis.server.service.basic.BaseResult;
import com.facetuis.server.service.basic.BasicService;
import com.facetuis.server.service.payment.PaymentService;
import com.facetuis.server.utils.PayCommonUtil;
import com.facetuis.server.utils.RandomUtils;
import com.facetuis.server.utils.XmlUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static org.apache.http.client.fluent.Request.*;

@Service
public class WechatPayService extends BasicService {

    private static final Logger logger = Logger.getLogger(WechatPayService.class.getName());

    @Autowired
    private PaymentService paymentService;

    @Value("${wechat.app.id}")
    private String appid;
    @Value("${wechat.pay.mch.id}")
    private String mchid;
    @Value("${wechat.pay.key}")
    private String key;
    @Value("${wechat.pay.notify.url}")
    private String notifyUrl;
    @Value("${wechat.pay.unifiedorder.url}")
    private String unifiedorderUrl;


    public BaseResult unifiedorder(String body,String detail,String total_price,String ip,String userId,String productId){
        //生成订单号码   //body 就是title 商品标题 detail 描述
        String tradeNo = PayUtils.getTradeNo();
        String random = RandomUtils.random(24);
        BaseResult baseResult  = new BaseResult();
        HashMap<String,String> map = new HashMap<>();
        map.put("appid",appid);
        map.put("mch_id",mchid);
        map.put("nonce_str",random);
        map.put("body",body);
        map.put("out_trade_no", tradeNo);
        map.put("total_fee",total_price);
        map.put("spbill_create_ip","127.0.0.1");
        map.put("notify_url",getServerUrl(notifyUrl));
        map.put("trade_type","APP");
        String sign = PayCommonUtil.createSign(map,key);
        map.put("sign",sign);
        String xml = PayCommonUtil.getRequestXml(map);
        try {
            xml = new String(xml.getBytes(),"ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            String respone = Post(unifiedorderUrl)
                    .bodyString(xml, ContentType.APPLICATION_XML)
                    .execute()
                    .handleResponse(
                            new ResponseHandler<String>() {
                                @Override
                                public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                                    return EntityUtils.toString(response.getEntity(), Consts.UTF_8);
                                }
                            }
                    );
            Map<String,String> result = XmlUtils.doXMLParse(respone);
            logger.info("result:: " +  result.get("return_msg"));
            if(result.get("return_code").equals("SUCCESS")){
                // paymentService.save(tradeNo,total_amount, PayStatus.WAIT_PAY, PayType.ALIPAY);
                paymentService.save(tradeNo,body,total_price,PayStatus.WAIT_PAY,PayType.WECHAT_PAY,userId,productId);
                Map<String, String> parameterMap2 = new HashMap<>();
                parameterMap2.put("appid", appid);
                parameterMap2.put("partnerid",mchid);
                parameterMap2.put("prepayid", result.get("prepay_id"));
                parameterMap2.put("package", "Sign=WXPay");
                parameterMap2.put("noncestr", RandomUtils.random(24));
                //本来生成的时间戳是13位，但是ios必须是10位，所以截取了一下
                parameterMap2.put("timestamp", Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0,10)) + "");
                String sign2 = PayCommonUtil.createSign(parameterMap2,key);
                parameterMap2.put("sign", sign2);
                parameterMap2.put("out_trade_no",tradeNo);
                baseResult.setResult(parameterMap2);
                return  baseResult;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }
        return new BaseResult();
    }

    public BaseResult<String> checkNotify(String xml) {
        try {
            Map<String,String> map = XmlUtils.doXMLParse(xml);
            logger.info("AliPay callback sign result_code:: " + map.get("result_code"));
            if (map.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
                //PayCommonUtil.isTenpaySign(map,key); 验证签名
                String appid = map.get("appid");
                if(appid.equals(this.appid)){
                    String out_trade_no = map.get("out_trade_no");
                    Payment payment = paymentService.findByTradeNo(out_trade_no);
                    if(payment != null){
                        payment.setPayStatus(PayStatus.PAY_SUCCESS);
                        payment.setTradeNo(map.get("transaction_id"));
                        paymentService.updatePayment(payment);
                    }
                    String resWX = PayCommonUtil.setXML("SUCCESS", "OK");
                    return new BaseResult<String>(resWX);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BaseResult(600,"处理失败");
    }

    public Payment findByOutTradeNo(String outTradeNo){
        return paymentService.findByOutTradeNo(outTradeNo);
    }



}
