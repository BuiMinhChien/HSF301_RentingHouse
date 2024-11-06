package com.spring.mvc.dao;

import com.spring.mvc.entity.Contract;

import java.util.List;

public interface ContractDAO {
    public void save(Contract contract);
    public List<Contract> getAllContract();
    public Contract getContractById(int id);
    public Contract getContractByHouseId(int houseId);
    public void deleteContractById(int id);
    public void updateContract(Contract contract);
}
