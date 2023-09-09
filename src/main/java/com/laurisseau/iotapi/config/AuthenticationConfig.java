package com.laurisseau.iotapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthenticationConfig {

    @Value("${cloud.aws.credentials.cognito-secret-key}")
    private static String secretKey;
    @Value("${cloud.aws.credentials.cognito-client-id}")
    private static String clientId;

    @Bean
    public String getSecretKey() {return secretKey;}

    @Bean
    public String getClientId() {return clientId;}

}
