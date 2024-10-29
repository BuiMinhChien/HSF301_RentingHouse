package com.spring.mvc.service;

import com.spring.mvc.dto.AgentDTO;
import com.spring.mvc.entity.Agent;
import com.spring.mvc.entity.Paging;

import java.util.List;

public interface AgentService {
//    public List<AgentDTO> findByAccountNameStatus(String account, String name, String status, int pageNumber, int pageSize);
    public List<AgentDTO> findByAccountNameStatus(String account, String name, String status, Paging paging);
    public int getTotalRowsForSearch(String account, String name, String status);
//    public List<AgentDTO> findByAccountNameStatus(String account, String name, String status);
//    public List<AgentDTO> getAllAgent(int pageNumber, int pageSize);
//    public List<AgentDTO> getAllAgent();
    public boolean createAgent(Agent agent);
    public boolean updateAgent(int id, Agent agent);
    public boolean deleteAgent(int id);
    public AgentDTO getAgent(int id);
//    public List<AgentDTO> getAllAgentPagination(Paging paging);
//    public int getTotalRows();
}
