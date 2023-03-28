package com.lwl.wallet.service;

import com.lwl.wallet.dao.AppUserDao;
import com.lwl.wallet.dao.AppUserDaoImpl;
import com.lwl.wallet.domain.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.lwl.wallet.dao.exception.UserAlreadyExistsException;
import com.lwl.wallet.domain.AppUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserServiceImpl implements AppUserService{

    private final AppUserDao appUserDao;



    @Override
    public long addUser(AppUser appUser) {

        Assert.notNull(appUser,"App user can't be null");
        Assert.isTrue(StringUtils.hasText(appUser.getMobile()),"Mobile number can't be null or empty");
        Assert.isTrue(StringUtils.hasText(appUser.getEmail()),"Email can't be null or empty");
        AppUser existingUser = getUser(appUser.getMobile());
        if(existingUser!=null){
            log.error("User exists with given {} mobile number",appUser.getMobile());
            throw new UserAlreadyExistsException("User with mobile number already registered"+appUser.getMobile(),"1001-USER_EXISTS");
        }
        long id = appUserDao.insertUser(appUser);
        log.info("User created with id :{}",id);
        return id;
    }

    @Override
    public AppUser getUser(String mobile) {
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
