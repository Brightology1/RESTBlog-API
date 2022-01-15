package com.example.restblogapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@SpringBootApplication
public class RestBlogAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestBlogAppApplication.class, args);
    }

    @Bean
    public AuthenticationManager getAuthenticationManager(@Lazy AuthenticationManager authenticationManager) {
        return authenticationManager;
    }

    @Bean
    public TokenStore getTokenStore(){
        return new InMemoryTokenStore();
    }

}
