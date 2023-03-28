package com.lwl.wallet.service;

import com.lwl.wallet.domain.AppUser;

public interface AppUserService {

    long addUser(AppUser appUser);
    AppUser getUsers(String mobile);
    boolean deleteUser(String mobile);
    String getUsername(String mobile);
    String getPassword(String mobile);
    AppUser getUser(String username, String password);

}
