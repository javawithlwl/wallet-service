package com.lwl.wallet.dao.test;

import com.lwl.wallet.dao.AppUserDao;
import com.lwl.wallet.dao.AppUserDaoImpl;
import com.lwl.wallet.domain.AppUser;
import com.lwl.wallet.util.ConnectionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AppUserDaoImplTest {

    private AppUserDao appUserDao;

    @BeforeEach
    public void init() {
        clearTable();
        appUserDao = new AppUserDaoImpl();
    }

    @Test
    void addUserTest() {
        long id = appUserDao.insertUser(appUser());
        Assertions.assertTrue(id > 0);
    }


    public AppUser appUser() {
        AppUser appUser = new AppUser();
        appUser.setUsername("Lakshman");
        appUser.setPassword("lakshman@123");
        appUser.setMobile("9876543210");
        appUser.setEmail("lakshman.d@gmail.com");
        return appUser;
    }

    public void clearTable() {
        Connection con = null;
        Statement st = null;
        try {
            con = ConnectionUtil.getConnection();
            st = con.createStatement();
            st.executeUpdate("delete from wallet");
            st.executeUpdate("delete from appuser");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void selectUserTest() {
        String username = "Lakshman";
        String password = "lakshman@123";
        long id = appUserDao.insertUser(appUser());
        AppUser appUser = appUserDao.selectUser(username, password);
        System.out.println(appUser.getId() + "  " + appUser.getUsername() + "   " + appUser.getPassword() + "  " +
                appUser.getMobile() + "   " + appUser.getEmail());

    }

    @Test
    void selectUserTests() {
        String mobile = "9876543210";
        long id = appUserDao.insertUser(appUser());
        AppUser appUser = appUserDao.selectUser(mobile);
        System.out.println(appUser.getId() + "  " + appUser.getUsername() + "   " + appUser.getPassword() + "  " +
                appUser.getMobile() + "   " + appUser.getEmail());
    }

    @Test
    void getUsernameTest() {
        String mobile = "9876543210";
        long id = appUserDao.insertUser(appUser());
        System.out.println(appUserDao.getUsername(mobile));
    }

    @Test
    void getPasswordTest() {
        String mobile = "9876543210";
        long id = appUserDao.insertUser(appUser());
        System.out.println(appUserDao.getPassword(mobile));
    }
}
