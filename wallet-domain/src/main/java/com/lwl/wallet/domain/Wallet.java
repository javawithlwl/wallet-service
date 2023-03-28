package com.lwl.wallet.domain;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class Wallet {
    private long id;
    private String mobile;
    private LocalDate createdDate;
    private LocalDate expiryDate;
    private double balance;
    private int securityCode;

}
