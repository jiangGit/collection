package com.facetuis.server.dao.sms;

import com.facetuis.server.model.mobile.SmsMessage;
import com.facetuis.server.model.mobile.SmsModelCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsMessageRepository extends JpaRepository<SmsMessage,String> {

    SmsMessage findByMobileNumberAndModelCode (String mobileNumber, SmsModelCode code);
}
