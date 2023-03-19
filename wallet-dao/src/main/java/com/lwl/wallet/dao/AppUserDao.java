package com.lwl.wallet.dao;

import com.lwl.wallet.domain.AppUser;

public interface AppUserDao {

    long insertUser(AppUser appUser);

    AppUser selectUser(String mobile);

    boolean deleteUser(String mobile);

    String getUsername(String mobile);

    String getPassword(String mobile);

    AppUser selectUser(String username, String password);


}
