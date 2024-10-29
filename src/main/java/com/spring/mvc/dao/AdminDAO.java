package com.spring.mvc.dao;

import com.spring.mvc.entity.Admin;

public interface AdminDAO {
    public Admin findByEmailAndPassword(String email, String password);
}
