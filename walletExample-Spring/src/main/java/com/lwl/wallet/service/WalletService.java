package com.lwl.wallet.service;

import com.lwl.wallet.domine.Transaction;
import com.lwl.wallet.domine.Wallet;

import java.time.Month;
import java.util.List;

public interface WalletService {

    List<Transaction> getByMonth(int month);
    List<Transaction> getByMobile(String mobile);
    List<Wallet> getWallectList();


}
