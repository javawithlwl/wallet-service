package com.lwl.wallet.dao;

import com.lwl.wallet.domain.TransactionType;
import com.lwl.wallet.domain.Transactions;
import com.lwl.wallet.util.ConnectionUtil;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionDaoImpl implements  TransactionDao{
    @Override
    public List<Transactions> selectTransaction(String mobile) {
        List<Transactions> transactionsList = new ArrayList<>();
         Connection con = null;
         PreparedStatement ps = null;
         ResultSet rs = null;

         try {
             con = ConnectionUtil.getConnection();
             ps = con.prepareStatement("select id,fromMobile,toMobile,amount,transactionType from transactions where (fromMobile =? or toMobile=?)");
             ps.setString(1,mobile);
             ps.setString(2,mobile);
             rs= ps.executeQuery();
             while (rs.next()){
                 long id = rs.getLong("id");
                 String fromMobile = rs.getString("fromMobile");
                 String toMobile = rs.getString("toMobile");
                 float amount = rs.getFloat("amount");
                 String transactionType = rs.getString("transactionType");

                 Transactions transactions = new Transactions();
                 transactions.setId(id);
                 transactions.setFromMobile(fromMobile);
                 transactions.setToMobile(toMobile);
                 transactions.setAmount(amount);
                 transactions.setTransactionType(TransactionType.valueOf(transactionType));
                 transactionsList.add(transactions);
             }
         }catch (SQLException e){
             e.printStackTrace();
         }
        return transactionsList;
    }

    @Override
    public long insertTransaction(Transactions transactions) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        long id = 0;
        try {
            con = ConnectionUtil.getConnection();
            ps = con.prepareStatement("insert into transactions(fromMobile,toMobile,amount,transactionType) values(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,transactions.getFromMobile());
            ps.setString(2,transactions.getToMobile());
            ps.setFloat(3, (float) transactions.getAmount());
            ps.setString(4, String.valueOf((transactions.getTransactionType())));
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()){
                id = rs.getLong("id");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return id;
    }

}
