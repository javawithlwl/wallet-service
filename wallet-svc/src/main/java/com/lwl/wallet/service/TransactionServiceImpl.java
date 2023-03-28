package com.lwl.wallet.service;

import com.lwl.wallet.dao.TransactionDao;
import com.lwl.wallet.domain.Transactions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService{

    private final TransactionDao transactionDao;
    @Override
    public List<Transactions> selectTransaction(String mobile) {
        return transactionDao.selectTransaction(mobile);
    }

    @Override
    public long insertTransaction(Transactions transactions) {
        return transactionDao.insertTransaction(transactions);
    }
}
