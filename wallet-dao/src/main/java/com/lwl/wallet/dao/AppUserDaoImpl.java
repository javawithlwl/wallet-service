package com.lwl.wallet.dao;

import com.lwl.wallet.dao.exception.UserAlreadyExistsException;
import com.lwl.wallet.domain.AppUser;
import com.lwl.wallet.util.ConnectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
@RequiredArgsConstructor
public class AppUserDaoImpl implements AppUserDao {

    private final WalletDao walletDao;



    @Override
    public long insertUser(AppUser appUser) {
        if (isUserExists(appUser.getMobile())) {
            throw new UserAlreadyExistsException("User exists with given mobile number", "USER_EXISTS");
        }
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        long id = 0;
        try {
            con = ConnectionUtil.getConnection();
            pst = con.prepareStatement("insert into appuser(username,password,mobile,email,status) values(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, appUser.getUsername());
            pst.setString(2, appUser.getPassword());
            pst.setString(3, appUser.getMobile());
            pst.setString(4, appUser.getEmail());
            pst.setBoolean(5, false);
            pst.executeUpdate();
            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong("id");
            }
            walletDao.createWallet(appUser.getMobile());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public AppUser selectUser(String mobile) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        AppUser appUser = null;
        try {
            con = ConnectionUtil.getConnection();
            pst = con.prepareStatement("select id,username,password,mobile,email from appuser where mobile=?");
            pst.setString(1, mobile);
            rs = pst.executeQuery();
            if (rs.next()) {
                long id = rs.getLong("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String mobile1 = rs.getString("mobile");
                String email = rs.getString("email");
                appUser = new AppUser();
                appUser.setId(id);
                appUser.setUsername(username);
                appUser.setPassword(password);
                appUser.setMobile(mobile1);
                appUser.setEmail(email);
                return appUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appUser;
    }

    @Override
    public boolean deleteUser(String mobile) {
        Connection con = null;
        PreparedStatement ps = null;
        if (isUserExists(mobile)) {
            walletDao.deleteWallet(mobile);
            try {
                con = ConnectionUtil.getConnection();
                ps = con.prepareStatement("delete from user where mobile=?");
                ps.setString(1, mobile);
                System.out.println("Deleted the user");
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public String getUsername(String mobile) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String username = null;
        try {
            con = ConnectionUtil.getConnection();
            pst = con.prepareStatement("select username from appuser where mobile=? ");
            pst.setString(1, mobile);
            rs = pst.executeQuery();
            if (rs.next()) {
                username = rs.getString("username");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;
    }

    @Override
    public String getPassword(String mobile) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String password = null;
        try {
            con = ConnectionUtil.getConnection();
            pst = con.prepareStatement("select password from appuser where mobile=? ");
            pst.setString(1, mobile);
            rs = pst.executeQuery();
            if (rs.next()) {
                password = rs.getString("password");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password;
    }

    @Override
    public AppUser selectUser(String username, String password) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        AppUser appUser = null;
        try {
            con = ConnectionUtil.getConnection();
            pst = con.prepareStatement("select id,username,password,mobile,email from appuser where username=? and password =? ");
            pst.setString(1, username);
            pst.setString(2, password);
            rs = pst.executeQuery();
            if (rs.next()) {
                long id = rs.getLong("id");
                String username1 = rs.getString("username");
                String password1 = rs.getString("password");
                String mobile = rs.getString("mobile");
                String email = rs.getString("email");
                appUser = new AppUser();
                appUser.setId(id);
                appUser.setUsername(username1);
                appUser.setPassword(password1);
                appUser.setMobile(mobile);
                appUser.setEmail(email);
                return appUser;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appUser;
    }

    private boolean isUserExists(String mobile) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            con = ConnectionUtil.getConnection();
            pst = con.prepareStatement("select count(1) from appuser where mobile=?");
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


