package com.spring.mvc.service;

import com.spring.mvc.entity.Account;

public interface AccountService {
    Account findByUsername(String username);
    Account findByEmail(String email);
    boolean existsByUsername(String username);
}
