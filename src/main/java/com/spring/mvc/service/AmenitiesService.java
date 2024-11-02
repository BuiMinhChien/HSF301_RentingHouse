package com.spring.mvc.service;

import com.spring.mvc.entity.Amenities;

import java.util.List;

public interface AmenitiesService {
    public void save(Amenities amenities);
    public void update(Amenities amenities);
    public void delete(Amenities amenities);
    public Amenities findById(int id);
    public List<Amenities> findAll();
    public List<Amenities> findByName(String amenityName);
}
