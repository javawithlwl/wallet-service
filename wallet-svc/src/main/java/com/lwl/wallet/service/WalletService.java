package com.lwl.wallet.service;

import com.lwl.wallet.domain.TransactionType;
import com.lwl.wallet.domain.Wallet;

import java.util.List;

public interface WalletService {

      float addBalance(String mobile, String source, float balance);

      float getBalance(String mobile);

      Wallet getWallet(String mobile);

      long addWallet(String mobile);

      List<String> getActiveMobile();

      void deleteWallet(String mobile);

      void deleteAllWallets();

      float transferAmount(String fromMobile, String toMobile, float amount, TransactionType transactionType);
}
