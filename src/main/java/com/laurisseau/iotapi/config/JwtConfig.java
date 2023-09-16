package com.laurisseau.iotapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
//x
    @Value("${jwt.secret}")
    private static String jwtSecret;

    @Bean
    public String getJwtSecret() {return jwtSecret;}
}
