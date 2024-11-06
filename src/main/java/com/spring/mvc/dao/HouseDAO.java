package com.spring.mvc.dao;

import com.spring.mvc.entity.House;
import com.spring.mvc.entity.HouseOwner;

import java.util.List;

public interface HouseDAO {
    public void save(House house);
    public void delete(House house);
    public void update(House house);
    public House findById(int id);
    public List<House> findAll();
    public House findByName(String name);
    public House findTop3ByOrderByUpdatedDateDesc();
    public List<House> findByHouseOwner(HouseOwner houseOwner);
    public List<House> filterHouses(String status, String province, String district, String ward);
}
