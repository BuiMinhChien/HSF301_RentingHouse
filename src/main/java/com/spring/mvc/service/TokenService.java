package com.spring.mvc.service;

import com.spring.mvc.entity.Account;
import com.spring.mvc.entity.Token;

import java.util.Optional;

public interface TokenService {
    Optional<Token> findToken(String token);
    String createToken(Account account);
    void deleteToken(Token token);
}
