package com.spring.mvc.security;

import com.spring.mvc.entity.Account;
import com.spring.mvc.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.findByUsername(username);

            if (account == null || !username.equalsIgnoreCase(username)) {
                throw new UsernameNotFoundException("UserName not valid");
            }

            if (account.getPassword() == null || account.getPassword().isEmpty()) {
                throw new IllegalArgumentException("Password not valid");
            }

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(account.getRole().getName().toUpperCase());

        return new org.springframework.security.core.userdetails.User(account.getUsername(),account.getPassword(), List.of(authority));
    }
}
