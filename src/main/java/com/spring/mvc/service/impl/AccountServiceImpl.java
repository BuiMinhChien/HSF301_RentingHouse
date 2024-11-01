package com.spring.mvc.service.impl;

import com.spring.mvc.dao.AccountDAO;
import com.spring.mvc.entity.Account;
import com.spring.mvc.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "accountService")
@Transactional(propagation = Propagation.REQUIRED)
public class AccountServiceImpl implements AccountService {
    private final AccountDAO accountDAO;

    public AccountServiceImpl(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public Account findByUsername(String username) {
        try {
            return accountDAO.findByUsername(username);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public Account findByEmail(String email) {
        try {
            return accountDAO.findByEmail(email);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        try {
            return accountDAO.existsByUsername(username);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
