package com.lwl.wallet.dao.test;

import com.lwl.wallet.dao.PaymentDao;
import com.lwl.wallet.dao.PaymentDaoImpl;
import com.lwl.wallet.domain.Payments;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PaymentDaoImplTest {
    private PaymentDao paymentDao;

    public PaymentDaoImplTest(){
        paymentDao = new PaymentDaoImpl();
    }

    @Test
    void selectPaymentsTest(){
        String mobile = "9876543210";
        List<Payments> listPaymnts = paymentDao.selectPayments(mobile);
        System.out.println(listPaymnts);
    }

}
