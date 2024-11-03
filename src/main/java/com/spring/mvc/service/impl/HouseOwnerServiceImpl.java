package com.spring.mvc.service.impl;

import com.spring.mvc.dao.HouseOwnerDAO;
import com.spring.mvc.entity.HouseOwner;
import com.spring.mvc.service.HouseOwnerService;
import org.springframework.stereotype.Service;

@Service
public class HouseOwnerServiceImpl implements HouseOwnerService {
    private HouseOwnerDAO houseOwnerDAO;

    public HouseOwnerServiceImpl(HouseOwnerDAO houseOwnerDAO) {
        this.houseOwnerDAO = houseOwnerDAO;
    }

    @Override
    public void save(HouseOwner houseOwner) {
        houseOwnerDAO.save(houseOwner);
    }

    @Override
    public HouseOwner findByEmail(String email) {
        return null;
    }
}
