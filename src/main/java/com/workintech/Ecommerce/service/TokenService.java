package com.workintech.Ecommerce.service;

import com.workintech.Ecommerce.entity.Token;
import org.springframework.security.core.Authentication;

import java.time.Instant;


public interface TokenService {

    String generateJwtToken(Authentication authentication);
    Token saveToken(Token token);
}

