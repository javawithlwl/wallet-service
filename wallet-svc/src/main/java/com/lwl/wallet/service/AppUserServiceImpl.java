package com.lwl.wallet.service;

import com.lwl.wallet.dao.AppUserDao;
import com.lwl.wallet.dao.AppUserDaoImpl;
import com.lwl.wallet.dao.exception.ResourceNotFoundException;
import com.lwl.wallet.domain.AppUser;
import com.lwl.wallet.dto.AppUserDto;
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
public class AppUserServiceImpl implements AppUserService {

  private final AppUserDao appUserDao;


  @Override
  public long addUser(AppUser appUser) {

    Assert.notNull(appUser, "App user can't be null");
    Assert.isTrue(StringUtils.hasText(appUser.getMobile()), "Mobile number can't be null or empty");
    Assert.isTrue(StringUtils.hasText(appUser.getEmail()), "Email can't be null or empty");
    if (appUserDao.isUserExists(appUser.getMobile())) {
      throw new UserAlreadyExistsException("User exists with given mobile number", "USER_EXISTS");
    }
    long id = appUserDao.insertUser(appUser);
    log.info("User created with id :{}", id);
    return id;
  }

  @Override
  public AppUserDto getUser(String mobile) {
    if (!appUserDao.isUserExists(mobile)) {
      log.error("User with mobile :{} is not found",mobile);
      throw new ResourceNotFoundException("User not found with given mobile number "+mobile, "NOT_FOUND");
    }
    AppUserDto appUserDto = appUserDao.selectUser(mobile);
    log.info("User details found for the given mobile {}",mobile);
    return appUserDto;
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
    return appUserDao.selectUser(username, password);
  }


}
