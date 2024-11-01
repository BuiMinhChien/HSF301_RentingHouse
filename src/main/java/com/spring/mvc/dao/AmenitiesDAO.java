package com.spring.mvc.dao;

import com.spring.mvc.entity.Amenities;

import java.util.List;

public interface AmenitiesDAO {
    public void save(Amenities amenities);
    public void update(Amenities amenities);
    public void delete(Amenities amenities);
    public Amenities findById(int id);
    public List<Amenities> findAll();
    public List<Amenities> findByName(String amenityName);

}
