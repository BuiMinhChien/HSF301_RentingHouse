package com.spring.mvc.dao;

import com.spring.mvc.entity.Status;

import java.util.List;

public interface StatusDAO {
    public List<Status> getAllStatus();
    public Status getStatusByStatusId(int id);
}
