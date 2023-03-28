package com.lwl.wallet.controller;

import com.lwl.wallet.domain.AppUser;
import com.lwl.wallet.dto.GetUsersDto;
import com.lwl.wallet.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{mobile}")
    ResponseEntity<AppUser> getUser(@PathVariable("mobile") String mobile){
        AppUser user = appUserService.getUsers(mobile);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getuser")
    ResponseEntity<AppUser> getUsers(@RequestBody GetUsersDto getUsersDto){
        AppUser user = appUserService.getUser(getUsersDto.getUsername(),getUsersDto.getPassword());
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{mobile}")
    ResponseEntity<String> deleteUser(@PathVariable("mobile") String mobile){
        boolean userDel = appUserService.deleteUser(mobile);
        return ResponseEntity.ok(userDel?"User Deleted":"Something went wrong!");
    }

    @GetMapping("/username/{mobile}")
    ResponseEntity<String> getUserName(@PathVariable("mobile") String mobile){
        String appUser = appUserService.getUsername(mobile);
        return ResponseEntity.ok(appUser);
    }

    @GetMapping("/password/{mobile}")
    ResponseEntity<String> getPassword(@PathVariable("mobile") String mobile){
        String appUser = appUserService.getPassword(mobile);
        return ResponseEntity.ok(appUser);
    }
}
