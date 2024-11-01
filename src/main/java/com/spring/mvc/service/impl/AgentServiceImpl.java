package com.spring.mvc.service.impl;

import com.spring.mvc.dao.AgentDAO;
import com.spring.mvc.entity.Paging;
import com.spring.mvc.service.AgentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service(value = "agentService")
@Transactional(propagation = Propagation.REQUIRED)
public class AgentServiceImpl implements AgentService {
    private final AgentDAO agentDAO;
    public AgentServiceImpl(AgentDAO agentDAO) {
        this.agentDAO = agentDAO;
    }
}
