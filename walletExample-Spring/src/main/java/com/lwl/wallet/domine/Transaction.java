package com.lwl.wallet.domine;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Transaction {

    private long id;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
    @JsonProperty("from_mobile")
    private String fromMobile;
    @JsonProperty("to_mobile")
    private String toMobile;
    private double amount;

}
