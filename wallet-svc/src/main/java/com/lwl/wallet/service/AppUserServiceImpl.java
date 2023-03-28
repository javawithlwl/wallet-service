package com.lwl.wallet.service;

import com.lwl.wallet.dao.AppUserDao;
import com.lwl.wallet.dao.AppUserDaoImpl;
import com.lwl.wallet.domain.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService{

    private final AppUserDao appUserDao;


    @Override
    public long addUser(AppUser appUser) {
        return appUserDao.insertUser(appUser);
    }

    @Override
    public AppUser getUsers(String mobile) {
        return appUserDao.selectUser(mobile);
    }

    @Override
    public boolean deleteUser(String mobile) {
        return appUserDao.deleteUser(mobile);
    }

    @Override
    public String getUsername(String mobile) {
        return appUserDao.getUsername(mobile);
    }

    @Override
    public String getPassword(String mobile) {
        return appUserDao.getPassword(mobile);
    }

    @Override
    public AppUser getUser(String username, String password) {
        return appUserDao.selectUser(username,password);
    }


}
