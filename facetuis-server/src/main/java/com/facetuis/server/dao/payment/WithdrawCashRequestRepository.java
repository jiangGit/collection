package com.facetuis.server.dao.payment;

import com.facetuis.server.model.pay.Payment;
import com.facetuis.server.model.pay.WithdrawCashRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WithdrawCashRequestRepository extends JpaRepository<WithdrawCashRequest,String> {
}
