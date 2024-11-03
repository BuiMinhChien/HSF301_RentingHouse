package com.spring.mvc.service;

import com.spring.mvc.entity.HouseOwner;

import java.util.List;

public interface HouseOwnerService {
    public void save(HouseOwner houseOwner);
    public HouseOwner findByEmail(String email);
    public List<HouseOwner> findAll();
    public HouseOwner findById(int id);
}

