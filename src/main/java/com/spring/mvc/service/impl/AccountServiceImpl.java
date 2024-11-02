package com.spring.mvc.service.impl;

import com.spring.mvc.dao.AccountDAO;
import com.spring.mvc.entity.Account;
import com.spring.mvc.entity.Role;
import com.spring.mvc.service.AccountService;
import com.spring.mvc.service.RoleService;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.Date;

import static com.spring.mvc.entity.ERole.ROLE_CUSTOMER;

@Service(value = "accountService")
@Transactional(propagation = Propagation.REQUIRED)
public class AccountServiceImpl implements AccountService {
    private final AccountDAO accountDAO;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleService roleService;


    public AccountServiceImpl(AccountDAO accountDAO, BCryptPasswordEncoder passwordEncoder, RoleService roleService) {
        this.accountDAO = accountDAO;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
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

    @Override
    public boolean save(Account account) {
        try {
            accountDAO.save(account);
            return true;
        } catch (Exception ex) {
            throw ex;
        }
    }


    @Override
    public boolean existsByEmail(String email) {
        try {
            return accountDAO.existsByEmail(email);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
