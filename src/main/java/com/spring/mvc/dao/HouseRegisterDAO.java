package com.spring.mvc.dao;

import com.spring.mvc.entity.House;
import com.spring.mvc.entity.HouseRegister;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public interface HouseRegisterDAO {
    public void save(HouseRegister register);
    public void update(HouseRegister register);
    public HouseRegister getByRegisterId(int id);
    public HouseRegister getByHouseIdAccountId(int houseId, int accountId);
    public List<HouseRegister> getAllByAccountId(int accountId);

}
