package com.spring.mvc.service;

import com.spring.mvc.dto.StatusDTO;
import com.spring.mvc.entity.Status;

import java.util.List;

public interface StatusService {
    public List<StatusDTO> getAllStatus();
    public Status getStatusById(int id);
}
