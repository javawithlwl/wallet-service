package com.lwl.wallet.service;

import com.lwl.wallet.dao.WalletDao;
import com.lwl.wallet.dao.WalletDaoImpl;
import com.lwl.wallet.domain.TransactionType;
import com.lwl.wallet.domain.Wallet;

import java.util.List;

public class WalletServiceImpl implements WalletService{
    private WalletDao walletDao;

    public WalletServiceImpl() {
        walletDao = new WalletDaoImpl();
    }


    @Override
    public float addBalance(String mobile, String source, float balance) {
        return walletDao.insertBalance(mobile,source,balance);
    }

    @Override
    public float getBalance(String mobile) {
        return walletDao.selectBalance(mobile);
    }

    @Override
    public Wallet getWallet(String mobile) {
        return walletDao.selectWallet(mobile);
    }

    @Override
    public long addWallet(String mobile) {
        return walletDao.createWallet(mobile);
    }

    @Override
    public List<String> getActiveMobile() {
        return walletDao.getActiveMobile();
    }

    @Override
    public void deleteWallet(String mobile) {
        walletDao.deleteWallet(mobile);
    }

    @Override
    public void deleteAllWallets() {
        walletDao.deleteAllWallets();
    }

    @Override
    public float transferAmount(String fromMobile, String toMobile, float amount, TransactionType transactionType) {
        return walletDao.transferAmount(fromMobile, toMobile, amount, transactionType);
    }
}
