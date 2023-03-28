package com.lwl.wallet.service;

import com.lwl.wallet.dao.WalletDao;
import com.lwl.wallet.dao.WalletDaoImpl;
import com.lwl.wallet.domain.TransactionType;
import com.lwl.wallet.domain.Wallet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WalletServiceImpl implements WalletService{
    private final WalletDao walletDao;

    @Override
    public float addBalance(String mobile, String source, float balance) {
        Assert.isTrue(StringUtils.hasText(mobile),"Mobile can not be null or empty");
        Wallet wallet = walletDao.selectWallet(mobile);
        if(wallet==null){
            log.error("Wallet details are not found for {}",mobile);
            throw new IllegalArgumentException("Wallet is not found for the given number :"+mobile);
        }
        float currentBalance=walletDao.insertBalance(mobile,source,balance);
        log.info("Wallet {} has balance {}",wallet.getId(),balance);
        return balance;
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
    public boolean deleteWallet(String mobile) {
        return walletDao.deleteWallet(mobile);
    }

    @Override
    public void deleteAllWallets() {
        walletDao.deleteAllWallets();
    }

    @Override
    public float transferAmount(String fromMobile, String toMobile, float amount) {
        return walletDao.transferAmount(fromMobile, toMobile, amount);
    }
}
