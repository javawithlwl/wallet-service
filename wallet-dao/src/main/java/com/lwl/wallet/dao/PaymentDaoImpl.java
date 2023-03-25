package com.lwl.wallet.dao;

import com.lwl.wallet.domain.Payments;
import com.lwl.wallet.domain.TransactionType;
import com.lwl.wallet.util.ConnectionUtil;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentDaoImpl implements PaymentDao{
    @Override
    public long insertPayment(Payments payments) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        long id = 0;
        try {
            con = ConnectionUtil.getConnection();
            ps = con.prepareStatement("insert into payments(source,toMobile,amount,transactionType) values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,payments.getSource());
            ps.setString(2,payments.getToMobile());
            ps.setFloat(3, (float) payments.getAmount());
            ps.setString(4, String.valueOf(payments.getTransactionType()));
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()){
                id = rs.getLong("id");
                System.out.println("payment is done with id "+id);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public List<Payments> selectPayments(String mobile) {
        List<Payments> paymentsList = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
             con = ConnectionUtil.getConnection();
             ps= con.prepareStatement("select id,source,toMobile,amount,transactionType from payments where toMobile = ?");
             ps.setString(1,mobile);
             rs= ps.executeQuery();
             if (rs.next()) {
                 long id = rs.getLong("id");
                 String source = rs.getString("source");
                 String toMobile = rs.getString("toMobile");
                 float amount = rs.getFloat("amount");
                 String transactionType = rs.getString("transactionType");

                 Payments payments = new Payments();
                 payments.setId(id);
                 payments.setSource(source);
                 payments.setToMobile(toMobile);
                 payments.setAmount(amount);
                 payments.setTransactionType(TransactionType.valueOf(transactionType));
                 paymentsList.add(payments);

             }
         }catch (SQLException e){
             e.printStackTrace();
         }
        return paymentsList;
    }
}
