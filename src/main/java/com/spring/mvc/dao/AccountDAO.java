package com.spring.mvc.dao;

import com.spring.mvc.entity.Account;
import com.spring.mvc.entity.News;

public interface AccountDAO {
    Account findByUsername(String username);
    Account findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    public void save(Account account);
    public void update(Account account);
    Account findById(int id);
}
