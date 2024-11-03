package com.spring.mvc.dao;

import com.spring.mvc.entity.House;
import com.spring.mvc.entity.HouseOwner;

import java.util.List;

public interface HouseOwnerDAO {
    public void save(HouseOwner houseOwner);
    public void update(HouseOwner houseOwner);
    public void delete(HouseOwner houseOwner);
    public HouseOwner findById(int id);
    public List<HouseOwner> findAll();
    public List<HouseOwner> findByName(String houseName);
    public HouseOwner findByEmail(String email);


}
