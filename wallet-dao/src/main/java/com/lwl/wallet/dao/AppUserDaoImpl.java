package com.lwl.wallet.dao;

import com.lwl.wallet.dao.exception.ResourceNotFoundException;
import com.lwl.wallet.dao.exception.UserAlreadyExistsException;
import com.lwl.wallet.domain.AppUser;
import com.lwl.wallet.dto.AppUserDto;
import com.lwl.wallet.util.ConnectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
@RequiredArgsConstructor
public class AppUserDaoImpl implements AppUserDao {

    private final WalletDao walletDao;
    private final JdbcTemplate jdbcTemplate;


    @Override
    public long insertUser(AppUser appUser) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement pst = con.prepareStatement("insert into appuser(username,password,mobile,email,status) values(?,?,?,?,?)",new String[]{"id"});
            pst.setString(1, appUser.getUsername());
            pst.setString(2, appUser.getPassword());
            pst.setString(3, appUser.getMobile());
            pst.setString(4, appUser.getEmail());
            pst.setBoolean(5, false);
            return pst;
        },keyHolder);
        long id = keyHolder.getKey().longValue();
        return id;
    }

    @Override
    public AppUserDto selectUser(String mobile) {
            if(isUserExists(mobile)){
                AppUserDto user = jdbcTemplate.queryForObject("select id,username,mobile,email,status from appuser where mobile=?",
                    getRowMapperAppUserDto(), mobile);
                return user;
            }
            throw new ResourceNotFoundException("User is not found for the given mobile number "+mobile,"USER_NOT_FOUND");
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

    public boolean isUserExists(String mobile) {
        int res = jdbcTemplate.queryForObject("select count(*) from appuser where mobile=?",Integer.class,mobile);
        return res != 0;
    }

    private RowMapper<AppUserDto> getRowMapperAppUserDto(){
       return (rs, rowNum) -> {
           AppUserDto obj = AppUserDto.builder()
               .status(rs.getBoolean("status"))
               .username(rs.getString("username"))
               .email(rs.getString("email"))
               .mobile(rs.getString("mobile"))
               .id(rs.getLong("id"))
               .build();
           return obj;
       };

    }
}


