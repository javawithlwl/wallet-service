package com.lwl.wallet.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoadAmountDto {

    private String mobile;
    private  String source;
    private float amount;
}
