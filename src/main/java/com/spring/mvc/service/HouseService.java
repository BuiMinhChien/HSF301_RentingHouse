package com.spring.mvc.service;

import com.spring.mvc.entity.House;

import java.util.List;

public interface HouseService {
    public void save(House house);
    public void delete(House house);
    public void update(House house);
    public House findById(int id);
    public List<House> getAllHouses();
    public House findByName(String name);
    public House findTop3ByOrderByUpdatedDateDesc();
}
