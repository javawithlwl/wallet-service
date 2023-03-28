package com.lwl.wallet.service;

import com.lwl.wallet.domain.Transactions;

import java.util.List;

public interface TransactionService {
    List<Transactions> selectTransaction(String mobile);
    long insertTransaction(Transactions transactions);
}
