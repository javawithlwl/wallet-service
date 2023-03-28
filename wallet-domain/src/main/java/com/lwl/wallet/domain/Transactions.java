package com.lwl.wallet.domain;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class Transactions {
    private long id;
    private String fromMobile;
    private String toMobile;
    private double amount;
    private TransactionType transactionType;


}
