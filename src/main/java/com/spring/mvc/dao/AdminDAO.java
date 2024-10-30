package com.spring.mvc.dao;

public interface AdminDAO {
    public Admin findByEmailAndPassword(String email, String password);
}
