package com.lwl.wallet.dao;

import com.lwl.wallet.domain.AppUser;
import com.lwl.wallet.dto.AppUserDto;

public interface AppUserDao {

    long insertUser(AppUser appUser);

    AppUserDto selectUser(String mobile);

    boolean deleteUser(String mobile);

    String getUsername(String mobile);

    String getPassword(String mobile);

    AppUser selectUser(String username, String password);
    boolean isUserExists(String mobile);


}
