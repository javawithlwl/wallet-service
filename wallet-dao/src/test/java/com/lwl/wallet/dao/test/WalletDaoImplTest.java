package com.lwl.wallet.dao.test;

import com.lwl.wallet.dao.WalletDao;
import com.lwl.wallet.dao.WalletDaoImpl;
import com.lwl.wallet.dao.exception.WalletAlreadyExistsException;
import com.lwl.wallet.domain.TransactionType;
import com.lwl.wallet.domain.Wallet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.lwl.wallet.domain.TransactionType.CR;

public class WalletDaoImplTest {

        private WalletDao walletDao;

        @BeforeEach
        public  void init(){
                walletDao = new WalletDaoImpl();
                walletDao.deleteAllWallets();
        }

        @Test
        void createWalletTest(){
                String mobile = "9036102111";
                String mobile2 ="9603724457";
                long id= walletDao.createWallet(mobile);
                long id2= walletDao.createWallet(mobile2);
                System.out.println("Wallet id :"+id2);

        }
        @Test
        void createWalletWithExistingMobileTest(){
                String mobile = "9036102111";
                walletDao.createWallet(mobile);
                Exception exception = Assertions.assertThrows(WalletAlreadyExistsException.class, () -> {
                        walletDao.createWallet(mobile);
                });
        }

        @Test
        void checkBalanceTest(){
                String mobile = "9036102111";
                long id= walletDao.createWallet(mobile);
                walletDao.insertBalance(mobile,"ICICI",5000);
                float balance = walletDao.selectBalance(mobile);
                Assertions.assertEquals(5000,balance);

                String mobile2 = "9603724457";
                long id2= walletDao.createWallet(mobile2);
                walletDao.insertBalance(mobile2,"ICICI",4000);
                float balance1 = walletDao.selectBalance(mobile2);

        }

        @Test
        void mobileListTest(){
                String mobile = "9036102111";
                long id= walletDao.createWallet(mobile);
                List<String> list = walletDao.getActiveMobile();
                for (String mobile1:list){
                        System.out.println(mobile1);
                }
        }
        @Test
        void selectWalletTest(){
                String mobile = "9036102111";
                long id= walletDao.createWallet(mobile);
                Wallet wallet = walletDao.selectWallet(mobile);
                System.out.println(wallet.getId()+"  "+wallet.getCreatedDate()+"   "+wallet.getExpiryDate()+"  "+mobile+"   "+wallet.getBalance()+"   "+wallet.getSecurityCode());
        }
        @Test
        void  transactionAmountTest(){
                String fromMobile ="9036102111";
                String toMobile = "9603724457";
                TransactionType transactionType = CR;
                float amount = 2000;
                walletDao.transferAmount(fromMobile,toMobile,amount,transactionType);
        }
}

