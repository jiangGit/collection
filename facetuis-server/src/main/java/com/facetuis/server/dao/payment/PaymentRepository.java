package com.facetuis.server.dao.payment;

import com.facetuis.server.model.mobile.SmsMessage;
import com.facetuis.server.model.pay.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository  extends JpaRepository<Payment,String> {

    Payment findByOutTradeNo(String tradeNo);
}
