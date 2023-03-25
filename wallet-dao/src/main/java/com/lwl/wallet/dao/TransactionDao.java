package com.lwl.wallet.dao;


import com.lwl.wallet.domain.Transactions;

import java.util.List;

public interface TransactionDao {

    List<Transactions> selectTransaction(String mobile);

    long insertTransaction(Transactions transactions);
}
