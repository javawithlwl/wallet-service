package com.lwl.wallet.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferAmountDto {
    private String fromMobile;
    private String toMobile;
    private float amount;
}
