package com.spring.mvc.service;

import com.spring.mvc.entity.Account;
import org.springframework.ui.Model;

public interface AccountService {
    Account findByUsername(String username);
    Account findByEmail(String email);
    Account findById(int id);
    boolean existsByUsername(String username);
    public boolean save(Account account);
    boolean existsByEmail(String email);
}
