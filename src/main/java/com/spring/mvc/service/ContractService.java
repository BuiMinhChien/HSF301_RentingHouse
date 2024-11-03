package com.spring.mvc.service;

import com.spring.mvc.entity.Contract;

import java.util.List;

public interface ContractService {
    public boolean save(Contract contract);
    public List<Contract> getAllContract();
    public Contract getContractById(int id);
    public Contract getContractByHouseId(int houseId);
    public boolean deleteContractById(int id);
    public void updateContract(Contract contract);
}
