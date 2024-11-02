package com.spring.mvc.service;

import com.spring.mvc.entity.House;

import java.util.List;

public interface HouseService {
    public void save(House house);
    public List<House> getAllHouses();
}
