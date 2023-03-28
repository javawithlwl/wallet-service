package com.lwl.wallet.service;

import com.lwl.wallet.dao.PaymentDao;
import com.lwl.wallet.dao.PaymentDaoImpl;
import com.lwl.wallet.dao.TransactionDao;
import com.lwl.wallet.dao.TransactionDaoImpl;
import com.lwl.wallet.domain.Payments;
import com.lwl.wallet.domain.Transactions;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConvertToCsv {
    private TransactionDao transactionDao;
    private PaymentDao paymentDao;

    public ConvertToCsv() {
        transactionDao = new TransactionDaoImpl();
        paymentDao = new PaymentDaoImpl();
    }

    public void toCSV() {

        String mobile = "9603724457";
        List<Transactions> transList = transactionDao.selectTransaction(mobile);
        List<Payments> paymentList = paymentDao.selectPayments(mobile);
        File file = new File("wallet-svc/src/main/resources/transactions.csv");

        String header[] = new String[]{"Id", "FromSource/fomMobile", "TO", "Amount", "Transaction Type"};
        List<String[]> totalTransactionsList = new ArrayList<>();
        totalTransactionsList.add(header);
        for (Transactions c : transList) {
            String[] cnt = new String[5];
            cnt[0] = String.valueOf(c.getId());
            cnt[1] = c.getFromMobile();
            cnt[2] = c.getToMobile();
            cnt[3] = String.valueOf(c.getAmount());
            cnt[4] = String.valueOf(c.getTransactionType());
            totalTransactionsList.add(cnt);
        }
        for (Payments p:paymentList){
            String[] cnt1 = new String[5];
            cnt1[0] = String.valueOf(p.getId());
            cnt1[1] = p.getSource();
            cnt1[2] = p.getToMobile();
            cnt1[3] = String.valueOf(p.getAmount());
            cnt1[4] = String.valueOf(p.getTransactionType());
            totalTransactionsList.add(cnt1);
        }

        try (
                PrintWriter pw = new PrintWriter(file)) {
            totalTransactionsList.stream()
                    .map(this::convertToCSV)
                    .forEach(pw::println);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        assert (file.exists());
    }
    private String convertToCSV(String[] data) {
        return Stream.of(data)
                .map(this::escapeSpecialCharacters)
                .collect(Collectors.joining(","));
    }
    private String escapeSpecialCharacters(String data) {
        String escapedData = data.replaceAll("\\R", " ");
        if (data.contains(",") || data.contains("\"") || data.contains("'")) {
            data = data.replace("\"", "\"\"");
            escapedData = "\"" + data + "\"";
        }
        return escapedData;
    }
}
