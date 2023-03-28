package com.lwl.wallet.dao;

import com.lwl.wallet.dao.exception.WalletAlreadyExistsException;
import com.lwl.wallet.domain.Payments;
import com.lwl.wallet.domain.TransactionType;
import com.lwl.wallet.domain.Transactions;
import com.lwl.wallet.domain.Wallet;
import com.lwl.wallet.util.ConnectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static com.lwl.wallet.domain.TransactionType.CR;
import static com.lwl.wallet.domain.TransactionType.DR;

@Repository
@RequiredArgsConstructor
public class WalletDaoImpl implements WalletDao {

    private final TransactionDao transactionDao;
    private final PaymentDao paymentDao;



    @Override
    public float insertBalance(String mobile, String source, float amount) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        float balance = 0;
        try {
            con = ConnectionUtil.getConnection();
            pst = con.prepareStatement("update wallet set balance=balance+? where mobile=?");
            pst.setFloat(1, amount);
            pst.setString(2, mobile);
            pst.executeUpdate();
            balance = selectBalance(mobile);
            Payments payments = new Payments();
            payments.setSource(source);
            payments.setToMobile(mobile);
            payments.setAmount(amount);
            payments.setTransactionType(CR);
            paymentDao.insertPayment(payments);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    @Override
    public float selectBalance(String mobile) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        float balance = 0;
        try {
            con = ConnectionUtil.getConnection();
            pst = con.prepareStatement("select balance from wallet where mobile=?");
            pst.setString(1, mobile);
            rs = pst.executeQuery();
            if (rs.next()) {
                balance = rs.getFloat("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtil.close(rs, pst, con);
        }
        return balance;
    }

    @Override
    public Wallet selectWallet(String mobile) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        Wallet wallet = null;
        if (walletExists(mobile)) {
            try {
                con = ConnectionUtil.getConnection();
                pst = con.prepareStatement("select id,mobile,created_date,expiry_date,security_code,balance from wallet where mobile =?");
                pst.setString(1, mobile);
                rs = pst.executeQuery();
                if (rs.next()) {
                    long id = rs.getLong("id");
                    String mobile1 = rs.getString("mobile");
                    Date createdDate = rs.getDate("created_date");
                    Date expiryDate = rs.getDate("expiry_date");
                    String balance = rs.getString("balance");
                    int securityCode = rs.getInt("security_code");
                    wallet = new Wallet();
                    wallet.setId(id);
                    wallet.setMobile(mobile1);
                    wallet.setCreatedDate(createdDate.toLocalDate());
                    wallet.setExpiryDate(expiryDate.toLocalDate());
                    wallet.setBalance(Double.parseDouble(balance));
                    wallet.setSecurityCode(securityCode);
                    return wallet;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return wallet;

    }

    @Override
    public long createWallet(String mobile) {

        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        long id = 0;
        try {
            if (walletExists(mobile)) {
                throw new WalletAlreadyExistsException("Wallet already exists with mobile :" + mobile, "WALLET_EXISTS");
            }
            con = ConnectionUtil.getConnection();
            pst = con.prepareStatement("insert into wallet(mobile,created_date,expiry_date,security_code,balance) values(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, mobile);
            pst.setDate(2, Date.valueOf(LocalDate.now()));
            pst.setDate(3, Date.valueOf(LocalDate.now().plusYears(2)));
            pst.setInt(4, ThreadLocalRandom.current().nextInt(100, 1000));
            pst.setFloat(5, 0);
            int c = pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong("id");
            }
            System.out.println("Wallet created with id " + id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;

    }

    @Override
    public List<String> getActiveMobile() {
        List<String> mobileList = new ArrayList<>();
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = ConnectionUtil.getConnection();
            pst = con.prepareStatement("select mobile from wallet");
            rs = pst.executeQuery();
            if (rs.next()) {
                String mobile = rs.getString("mobile");
                mobileList.add(mobile);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mobileList;
    }

    public boolean deleteWallet(String mobile) {
        Connection con = null;
        PreparedStatement ps = null;
        if (this.walletExists(mobile)) {
            try {
                con = ConnectionUtil.getConnection();
                ps = con.prepareStatement("delete from wallet where mobile = ?");
                ps.setString(1, mobile);
                System.out.println("deleted wallet");
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public void deleteAllWallets() {
        Connection con = null;
        Statement pst = null;
        try {
            con = ConnectionUtil.getConnection();
            pst = con.createStatement();
            int count = pst.executeUpdate("delete from wallet");
            System.out.println("Deleted " + count + " wallets");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public float transferAmount(String fromMobile, String toMobile, float amount) {
        float balance1 = 0;
        if (!walletExists(fromMobile)){
            System.out.println("Wallet is not existed on " +fromMobile);
        }else {
            balance1 = selectBalance(fromMobile);
        }

        if (walletExists(toMobile)){
            float balance2 = selectBalance(toMobile);
            Connection con = null;
            PreparedStatement ps = null;
            PreparedStatement ps2 = null;
            if (balance1 >= amount) {
                float totalBalance2 = balance2 + amount;
                float finalBalance = balance1 - amount;
                try {
                    con = ConnectionUtil.getConnection();
                    ps = con.prepareStatement("update wallet set balance =? where mobile = ?");
                    ps.setFloat(1, totalBalance2);
                    ps.setString(2, toMobile);
                    ps.executeUpdate();
                    ps2 = con.prepareStatement("update wallet set balance =? where mobile = ?");
                    ps2.setFloat(1, finalBalance);
                    ps2.setString(2, fromMobile);
                    ps2.executeUpdate();

                    Transactions transactions = new Transactions();
                    transactions.setAmount(amount);
                    transactions.setTransactionType(DR);
                    transactions.setFromMobile(fromMobile);
                    transactions.setToMobile(toMobile);
                    transactionDao.insertTransaction(transactions);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else{
                System.out.println("Wallet "+fromMobile +"doesnt have sufficient balance to tranfer");
            }
        }else {
            System.out.println("Wallet is not existed on " +toMobile);
        }
        return amount;
    }

    private boolean walletExists(String mobile) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            con = ConnectionUtil.getConnection();
            pst = con.prepareStatement("select count(1) from wallet where mobile=?");
            pst.setString(1, mobile);
            rs = pst.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) > 0) {
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
