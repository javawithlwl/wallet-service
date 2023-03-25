package com.lwl.wallet.controller;

import com.lwl.wallet.domain.AppUser;
import com.lwl.wallet.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final AppUserService appUserService;
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody AppUser appUser){
      long id = appUserService.addUser(appUser);
      String message = "User is registered with id "+id;
      return ResponseEntity.ok(message);
    }
}
