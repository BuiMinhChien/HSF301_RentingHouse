package com.spring.mvc.dao;

import com.spring.mvc.entity.Account;

public interface AccountDAO {
    Account findByUsername(String username);
    Account findByEmail(String email);
    boolean existsByUsername(String username);
}
