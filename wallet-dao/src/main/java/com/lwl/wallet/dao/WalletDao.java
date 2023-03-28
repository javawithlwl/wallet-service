package com.lwl.wallet.dao;

import com.lwl.wallet.domain.TransactionType;
import com.lwl.wallet.domain.Wallet;

import java.util.List;

public interface WalletDao {
    float insertBalance(String mobile, String source, float balance);

    float selectBalance(String mobile);

    Wallet selectWallet(String mobile);

    long createWallet(String mobile);

    List<String> getActiveMobile();

    boolean deleteWallet(String mobile);

    void deleteAllWallets();

    float transferAmount(String fromMobile, String toMobile, float amount);

}
