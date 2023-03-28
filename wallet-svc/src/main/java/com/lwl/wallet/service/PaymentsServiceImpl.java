package com.lwl.wallet.service;

import com.lwl.wallet.dao.PaymentDao;
import com.lwl.wallet.domain.Payments;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentsServiceImpl implements PaymentsService{

    private final PaymentDao paymentDao;
    @Override
    public long insertPayment(Payments payments) {
        return paymentDao.insertPayment(payments);
    }

    @Override
    public List<Payments> selectPayments(String mobile) {
        return paymentDao.selectPayments(mobile);
    }
}
