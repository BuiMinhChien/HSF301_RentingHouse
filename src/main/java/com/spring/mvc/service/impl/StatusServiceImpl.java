package com.spring.mvc.service.impl;

import com.spring.mvc.dao.StatusDAO;
import com.spring.mvc.dto.StatusDTO;
import com.spring.mvc.service.StatusService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(value = "statusService")
@Transactional(propagation = Propagation.REQUIRED)
public class StatusServiceImpl implements StatusService {
}
