package com.lwl.wallet.domain;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class Payments {

    private long id;
    private String source;
    private String toMobile;
    private double amount;
    private TransactionType transactionType;

}

