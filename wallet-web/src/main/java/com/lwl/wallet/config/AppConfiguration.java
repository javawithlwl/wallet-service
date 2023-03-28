package com.lwl.wallet.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

  @Bean
  public OpenAPI customOpenAPI(@Value("${app.description}") String appDescription,
                               @Value("${app.version:1.0.0}") String appVersion) {
    return new OpenAPI()
        .info(new Info()
            .title("Wallet API")
            .version(appVersion)
            .description(appDescription)
            .termsOfService("http://lwl.com/terms/")
            .license(new License().name("Apache 2.0").url("http://springdoc.org")));

  }
}
