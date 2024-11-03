package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.TokenDAO;
import com.spring.mvc.entity.Account;
import com.spring.mvc.entity.Token;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Repository(value = "tokenDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class TokenDAOImpl implements TokenDAO {

    private final SessionFactory sessionFactory;

    public TokenDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public Optional<Token> findByToken(String token) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Token WHERE token = :token";
        Token result = session.createQuery(hql, Token.class)
                .setParameter("token", token)
                .uniqueResult();
        return Optional.ofNullable(result);
    }


    public String createToken(Account account) {
        String token = UUID.randomUUID().toString();
        Token resetToken = new Token();
        resetToken.setToken(token);
        resetToken.setAccount(account);
        resetToken.setExpiryDate(new Date(System.currentTimeMillis() + 3600 * 1000)); // Hết hạn sau 1 giờ
        sessionFactory.getCurrentSession().save(resetToken);
        return token;
    }

    @Override
    public void deleteToken(Token token) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(token);  // Directly delete the managed entity
    }

}
