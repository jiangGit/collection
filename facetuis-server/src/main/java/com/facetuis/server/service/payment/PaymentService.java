package com.facetuis.server.service.payment;

import com.facetuis.server.dao.payment.PaymentRepository;
import com.facetuis.server.model.pay.PayStatus;
import com.facetuis.server.model.pay.PayType;
import com.facetuis.server.model.pay.Payment;
import com.facetuis.server.service.basic.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PaymentService extends BasicService {


    @Autowired
    private PaymentRepository paymentRepository;

    /**
     * 创建支付信息
     * @param tradeNo
     * @param productTitle
     * @param amount
     * @param payStatus
     * @param payType
     */
    public void save(String tradeNo,String productTitle,String amount, PayStatus payStatus, PayType payType,String userId,String productId){
        Payment pay = new Payment();
        pay.setAmount(amount);//价格
        pay.setProductTitle(productTitle);//商品标题
        pay.setOutTradeNo(tradeNo);//订单号
        pay.setPayStatus(payStatus);//支付状态
        pay.setPayType(payType); //
        pay.setUuid(UUID.randomUUID().toString());
        pay.setUserId(userId);
        pay.setProductId(productId);
        paymentRepository.save(pay);
    }

    public Payment findByTradeNo(String tradeNo){
        return paymentRepository.findByOutTradeNo(tradeNo);
    }


    public void updatePayment(Payment payment){
        paymentRepository.save(payment);
    }


    public Payment findByOutTradeNo(String outTradeNo){
        return paymentRepository.findByOutTradeNo(outTradeNo);
    }

}
