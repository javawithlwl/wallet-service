package com.lwl.wallet.service;

import com.lwl.wallet.domain.Payments;

import java.util.List;

public interface PaymentsService {

    long insertPayment(Payments payments);
    List<Payments> selectPayments(String mobile);
}
