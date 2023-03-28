package com.lwl.wallet.domine;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class User {

    private long id;
    private String name;
    private String email;
    private String mobile;
}
