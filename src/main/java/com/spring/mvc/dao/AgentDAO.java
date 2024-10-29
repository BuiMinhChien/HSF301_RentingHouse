package com.spring.mvc.dao;

import com.spring.mvc.entity.Agent;
import com.spring.mvc.entity.Paging;

import java.util.List;

public interface AgentDAO {
//    public List<Agent> findByAccountNameStatus(String account, String name, String status, int pageNumber, int pageSize);
    public List<Agent> findByAccountNameStatus(String account, String name, String status, Paging paging);
    public int getTotalRowsForSearch(String account, String name, String status);
//    public List<Agent> findByAccountNameStatus(String account, String name, String status);
//    public List<Agent> getAllAgent(int pageNumber, int pageSize);
    public List<Agent> getAllAgent();
    public List<Agent> getAllAgentPagination(Paging paging);
    public void createAgent(Agent agent);
    public void updateAgent(int id, Agent agent);
    public Agent getAgent(int id);
    public void deleteAgent(int id);
//    public int getTotalRows();
}
