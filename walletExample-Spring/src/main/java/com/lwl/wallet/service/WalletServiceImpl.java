package com.lwl.wallet.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.lwl.wallet.domine.Transaction;
import com.lwl.wallet.domine.User;
import com.lwl.wallet.domine.Wallet;
import com.lwl.wallet.util.JsonUserUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService{

    private JsonUserUtil jsonUserUtil;
    List<Transaction> transactionList = new ArrayList<>();
    List<Wallet> walletList = new ArrayList<>();
    List<User> userList = new ArrayList<>();

    @Autowired
    public WalletServiceImpl(JsonUserUtil jsonUserUtil) {
        this.jsonUserUtil = jsonUserUtil;
    }

    @PostConstruct
    public void init(){
        TypeReference<List<Wallet>> walletType = new TypeReference<List<Wallet>>() {};
        walletList.addAll(jsonUserUtil.readFromJson("/wallet_data.json",walletType));
        System.out.println(walletList.size());
        TypeReference<List<Transaction>> transactionT = new TypeReference<List<Transaction>>() {};
        transactionList.addAll(jsonUserUtil.readFromJson("/txn_data.json",transactionT));
        System.out.println(transactionList.size());
        TypeReference<List<User>> userT = new TypeReference<List<User>>() {};
        userList.addAll(jsonUserUtil.readFromJson("/user_data.json",userT));
       System.out.println(userList.size());
        updateWalletBalance();
    }

    @PreDestroy
    public void clear(){
        walletList.clear();
        transactionList.clear();
        userList.clear();
    }
    @Override
    public List<Transaction> getByMonth(int month) {
        List<Transaction> transactionList1 = transactionList.stream().filter(ele->ele.getDate().getMonth()==Month.of(month)).collect(Collectors.toList());
        return transactionList1;

    }

    @Override
    public List<Transaction> getByMobile(String mobile) {
        List<Transaction> transactionList1 = transactionList.stream().filter(ele->ele.getFromMobile().equalsIgnoreCase(mobile)).collect(Collectors.toList());
        List<Transaction> transactionList2 = transactionList.stream().filter(ele->ele.getToMobile().equalsIgnoreCase(mobile)).collect(Collectors.toList());

        List<Transaction> transactionsaF = new ArrayList<>();
        transactionsaF.addAll(transactionList1);
        transactionsaF.addAll(transactionList2);
        return transactionsaF;
    }

    @Override
    public List<Wallet> getWallectList() {
       // List<Wallet> list= walletList.stream().collect(Collectors.toList());
        return walletList;
    }

//    private List<Wallet> addBalanceToAll(double amount){
//        List<Wallet> walletList1 = new ArrayList<>();
//        List<String> mobileList = walletList.stream().map(ele->ele.getMobile()).collect(Collectors.toList());
//        for (String mb:mobileList){
//            List<Wallet> wallet1 = walletList.stream().filter(ele -> ele.getMobile().equalsIgnoreCase(mb)).collect(Collectors.toList());
//
//            for (Wallet wallet : wallet1) {
//                long id = wallet.getId();
//                double totalAmount = wallet.getBalance() + amount;
//                LocalDate currentDate = wallet.getCreatedDate();
//                String mobile1 = wallet.getMobile();
//
//                Wallet wal = new Wallet();
//                wal.setId(id);
//                wal.setMobile(mobile1);
//                wal.setCreatedDate(currentDate);
//                wal.setBalance(amount);
//
//                walletList1.add(wal);
//            }
//        }
//        return walletList1;
//    }
    private void updateWalletBalance(){
        for(Transaction t:transactionList){
            String fromMobile = t.getFromMobile();
            String toMobile = t.getToMobile();
            Wallet fromWallet = getWallet(fromMobile);
            Wallet toWallet = getWallet(toMobile);
            fromWallet.setBalance(fromWallet.getBalance()-t.getAmount());
            toWallet.setBalance(toWallet.getBalance()+t.getAmount());
        }
    }
    private Wallet getWallet(String mobile) {
        for(Wallet wallet:walletList){
            if(wallet.getMobile().equals(mobile)){
                return wallet;
            }
        }
        throw new IllegalArgumentException("Wallet is not found for given mobile number "+mobile);
    }



}
