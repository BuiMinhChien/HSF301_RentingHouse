package com.spring.mvc.dao;

import com.spring.mvc.entity.Account;
import com.spring.mvc.entity.Token;

import java.util.Optional;

public interface TokenDAO {
    Optional<Token> findByToken(String token);
    String createToken(Account account);
    void deleteToken(Token token);
}
