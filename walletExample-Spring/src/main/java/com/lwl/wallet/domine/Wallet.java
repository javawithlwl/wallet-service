package com.lwl.wallet.domine;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class Wallet {
    private long id;
    @JsonProperty("created_date")
    private LocalDate createdDate;
    private String mobile;
    private double balance;

}
