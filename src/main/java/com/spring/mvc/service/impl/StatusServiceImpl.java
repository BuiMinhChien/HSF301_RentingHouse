package com.spring.mvc.service.impl;

import com.spring.mvc.dao.StatusDAO;
import com.spring.mvc.dto.StatusDTO;
import com.spring.mvc.entity.Status;
import com.spring.mvc.service.StatusService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "statusService")
@Transactional(propagation = Propagation.REQUIRED)
public class StatusServiceImpl implements StatusService {
    private final StatusDAO statusDAO;
    public StatusServiceImpl(StatusDAO statusDAO) {
        this.statusDAO = statusDAO;
    }
    public List<StatusDTO> getAllStatus() {
        try {
            List<Status> list = statusDAO.getAllStatus();

            List<StatusDTO> statusDTOs = new ArrayList<StatusDTO>();
            StatusDTO statusDTO = null;
            for(Status status : list) {
                statusDTO = new StatusDTO();
                statusDTO.setStatusId(status.getStatusId());
                statusDTO.setStatusName(status.getStatusName());
                statusDTOs.add(statusDTO);
            }
            return statusDTOs;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public Status getStatusById(int id) {
        return statusDAO.getStatusByStatusId(id);
    }
}
