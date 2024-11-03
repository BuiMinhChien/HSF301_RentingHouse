package com.spring.mvc.service.impl;

import com.spring.mvc.dao.TagDAO;
import com.spring.mvc.dao.TokenDAO;
import com.spring.mvc.entity.Account;
import com.spring.mvc.entity.Token;
import com.spring.mvc.service.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service(value = "tokenService")
@Transactional(propagation = Propagation.REQUIRED)
public class TokenServiceImpl implements TokenService {
    private final TokenDAO tokenDAO;

    public TokenServiceImpl(TokenDAO tokenDAO) {
        this.tokenDAO = tokenDAO;
    }

    @Override
    public Optional<Token> findToken(String token) {
        return tokenDAO.findByToken(token);
    }

    @Override
    public String createToken(Account account) {
        return tokenDAO.createToken(account);
    }

    @Override
    public void deleteToken(Token token) {
        tokenDAO.deleteToken(token);
    }
}
