package com.lwl.wallet.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class AppUserDto {
    public long id;
    public String username;
    public String email;
    public String mobile;
    public boolean status;
}
