package com.lwl.wallet.dao;

import com.lwl.wallet.domain.Payments;

import java.util.List;

public interface PaymentDao {
    long insertPayment(Payments payments);
    List<Payments> selectPayments(String mobile);

}
