package com.spring.mvc.service;

import com.spring.mvc.entity.Contract;

import java.util.List;

public interface ContractService {
    public boolean save(Contract contract);
    public List<Contract> getAllContract();
    public Contract getContractById(int id);
    public boolean deleteContractById(int id);
}
