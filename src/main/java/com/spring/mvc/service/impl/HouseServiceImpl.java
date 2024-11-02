package com.spring.mvc.service.impl;

import com.spring.mvc.dao.HouseDAO;
import com.spring.mvc.entity.House;
import com.spring.mvc.service.HouseService;
import org.springframework.stereotype.Service;

@Service
public class HouseServiceImpl implements HouseService {
    private HouseDAO houseDAO;

    public HouseServiceImpl(HouseDAO houseDAO) {
        this.houseDAO = houseDAO;
    }

    @Override
    public void save(House house) {
        houseDAO.save(house);
    }
}
