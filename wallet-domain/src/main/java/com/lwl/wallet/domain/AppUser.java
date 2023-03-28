package com.lwl.wallet.domain;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AppUser {
    public long id;
    public String username;
    public String password;
    public String email;
    public String mobile;
    public boolean status;
}
