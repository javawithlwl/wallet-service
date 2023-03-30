package com.lwl.wallet.service;

import com.lwl.wallet.domain.AppUser;
import com.lwl.wallet.dto.AppUserDto;

public interface AppUserService {

    long addUser(AppUser appUser);
    AppUserDto getUser(String mobile);
    boolean deleteUser(String mobile);
    String getUsername(String mobile);
    String getPassword(String mobile);
    AppUser getUser(String username, String password);

}
