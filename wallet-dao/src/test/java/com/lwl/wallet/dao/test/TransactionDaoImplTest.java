package com.lwl.wallet.dao.test;

import com.lwl.wallet.dao.TransactionDao;
import com.lwl.wallet.dao.TransactionDaoImpl;
import com.lwl.wallet.dao.WalletDao;
import com.lwl.wallet.dao.WalletDaoImpl;
import com.lwl.wallet.domain.TransactionType;
import com.lwl.wallet.domain.Transactions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.lwl.wallet.domain.TransactionType.DR;

public class TransactionDaoImplTest {

    private TransactionDao transactionDao;
    public WalletDao walletDao;

    public TransactionDaoImplTest(){
        transactionDao = new TransactionDaoImpl();
        walletDao = new WalletDaoImpl();
    }

    void  transactionAmountTest(){
        String fromMobile ="9036102111";
        String toMobile = "9603724457";
        TransactionType transactionType = DR;
        float amount = 2000;
        walletDao.transferAmount(fromMobile,toMobile,amount,transactionType);
    }

     @Test
    void insertTransactions(){
        transactionAmountTest();
    }

    @Test
    void selectTransactionList(){
        String fromMobile = "9876543210";
        List<Transactions> transacList = transactionDao.selectTransaction(fromMobile);
        System.out.println(transacList);
    }

}
