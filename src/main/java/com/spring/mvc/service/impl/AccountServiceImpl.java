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
import java.util.NoSuchElementException;

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

    @Override
    public Account findById(int id) {
        try {
            return accountDAO.findById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(Account account) {
        try {
            Account existingAccount = accountDAO.findById(account.getId());
            if (existingAccount != null) {
                // Update fields if they are modified
              //  existingAccount.setPassword(passwordEncoder.encode(account.getPassword())); // encode password if modified
                existingAccount.setImage(account.getImage());
                // Save the updated account
                accountDAO.update(existingAccount);
            } else {
                throw new RuntimeException("Account not found");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    public void changePassword(String username, String oldPassword, String newPassword) {

        Account existingAccount = accountDAO.findByUsername(username);

        if (existingAccount == null) {
            throw new NoSuchElementException("User not found.");
        }

        // Validate the old password
        if (!passwordEncoder.matches(oldPassword, existingAccount.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect.");
        }

        // Update the password with the new encoded password
        existingAccount.setPassword(passwordEncoder.encode(newPassword));
        accountDAO.update(existingAccount);
    }

    @Override
    public void updateProfile(Account account) {
        try {
            Account existingAccount = accountDAO.findById(account.getId());
            if (existingAccount != null) {
                // Update fields if they are modified

                // Save the updated account
                accountDAO.update(existingAccount);
            } else {
                throw new RuntimeException("Account not found");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

}
