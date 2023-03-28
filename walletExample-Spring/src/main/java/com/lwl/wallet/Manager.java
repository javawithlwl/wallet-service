package com.lwl.wallet;

import com.lwl.wallet.service.WalletService;
import com.lwl.wallet.service.WalletServiceImpl;
import lombok.ToString;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.lwl.wallet")
public class Manager {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(Manager.class);


        WalletService services = applicationContext.getBean(WalletServiceImpl.class);
        System.out.println(services.getWallectList());
    }




}
