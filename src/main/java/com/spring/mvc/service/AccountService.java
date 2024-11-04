package com.spring.mvc.service;

import com.spring.mvc.entity.Account;
import com.spring.mvc.entity.Notification;
import org.springframework.ui.Model;

public interface AccountService {
    Account findByUsername(String username);
    Account findByEmail(String email);
    boolean existsByUsername(String username);
    public boolean save(Account account);
    boolean existsByEmail(String email);
    Account findById(int id);
     void update(Account account);
     void updateWithNotification(Account account, Notification notification);
     void changePassword(String username, String oldPassword, String newPassword);
     void updateProfile(Account account);
     void changePass(Account account);
}
