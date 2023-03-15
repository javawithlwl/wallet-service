package com.lwl.wallet.service;

import java.io.File;

public interface WalletService {

      boolean transferAmount(long fromWalletId,long toWalletId,double amount);
      File exportStatement(String mobile);
      double getBalance(String mobile);
}
