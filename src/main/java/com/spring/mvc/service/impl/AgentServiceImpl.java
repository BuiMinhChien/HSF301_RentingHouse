package com.spring.mvc.service.impl;

import com.spring.mvc.dao.AgentDAO;
import com.spring.mvc.dto.AgentDTO;
import com.spring.mvc.entity.Agent;
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

//    public List<AgentDTO> findByAccountNameStatus(String account, String name, String status, int pageNumber, int pageSize) {
//        try {
//            List<Agent> list = agentDAO.findByAccountNameStatus(account, name, status, pageNumber, pageSize);
//            List<AgentDTO> agentDTOs = new ArrayList<AgentDTO>();
//            AgentDTO agentDTO = null;
//            for (Agent agent : list) {
//                agentDTO = new AgentDTO();
//                agentDTO.setAgentId(agent.getAgentId());
//                agentDTO.setEmail(agent.getEmail());
//                agentDTO.setAgentName(agent.getAgentName());
//                agentDTO.setAddress(agent.getAddress());
//                agentDTO.setStatus(agent.getStatus());
//                // Định dạng dd/MM/yyyy
//                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//                agentDTO.setRegistrationDate(formatter.format(agent.getRegistrationDate()));
//                agentDTO.setAccountBalance(agent.getAccountBalance()+"");
//                agentDTOs.add(agentDTO);
//            }
//            return agentDTOs;
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }

    @Override
    public List<AgentDTO> findByAccountNameStatus(String account, String name, String status, Paging paging) {
        try {
            List<Agent> list = agentDAO.findByAccountNameStatus(account, name, status, paging);
            List<AgentDTO> agentDTOs = new ArrayList<AgentDTO>();
            AgentDTO agentDTO = null;
            for (Agent agent : list) {
                agentDTO = new AgentDTO();
                agentDTO.setAgentId(agent.getAgentId());
                agentDTO.setEmail(agent.getEmail());
                agentDTO.setAgentName(agent.getAgentName());
                agentDTO.setAddress(agent.getAddress());
                agentDTO.setStatus(agent.getStatus());
                // Định dạng dd/MM/yyyy
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                agentDTO.setRegistrationDate(formatter.format(agent.getRegistrationDate()));
                agentDTO.setAccountBalance(agent.getAccountBalance()+"");
                agentDTOs.add(agentDTO);
            }
            return agentDTOs;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public int getTotalRowsForSearch(String account, String name, String status) {
        try {
            int result = agentDAO.getTotalRowsForSearch(account, name, status);
            return result;
        } catch (Exception ex) {
            throw ex;
        }
    }

//    public List<AgentDTO> findByAccountNameStatus(String account, String name, String status) {
//        try {
//            List<Agent> list = agentDAO.findByAccountNameStatus(account, name, status);
//
//            List<AgentDTO> agentDTOs = new ArrayList<AgentDTO>();
//            AgentDTO agentDTO = null;
//            for (Agent agent : list) {
//                agentDTO = new AgentDTO();
//                agentDTO.setAgentId(agent.getAgentId());
//                agentDTO.setEmail(agent.getEmail());
//                agentDTO.setAgentName(agent.getAgentName());
//                agentDTO.setAddress(agent.getAddress());
//                agentDTO.setStatus(agent.getStatus());
//                // Định dạng dd/MM/yyyy
//                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//                agentDTO.setRegistrationDate(formatter.format(agent.getRegistrationDate()));
//                agentDTO.setAccountBalance(agent.getAccountBalance()+"");
//                agentDTOs.add(agentDTO);
//            }
//            return agentDTOs;
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }

//    public List<AgentDTO> getAllAgent(int pageNumber, int pageSize) {
//        try {
//            List<Agent> list = agentDAO.getAllAgent(pageNumber, pageSize);
//
//            List<AgentDTO> agentDTOs = new ArrayList<AgentDTO>();
//            AgentDTO agentDTO = null;
//            for (Agent agent : list) {
//                agentDTO = new AgentDTO();
//                agentDTO.setAgentId(agent.getAgentId());
//                agentDTO.setEmail(agent.getEmail());
//                agentDTO.setAgentName(agent.getAgentName());
//                agentDTO.setAddress(agent.getAddress());
//                agentDTO.setStatus(agent.getStatus());
//                // Định dạng dd/MM/yyyy
//                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//                agentDTO.setRegistrationDate(formatter.format(agent.getRegistrationDate()));
//                agentDTO.setAccountBalance(agent.getAccountBalance()+"");
//                agentDTOs.add(agentDTO);
//            }
//            return agentDTOs;
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }
//    public List<AgentDTO> getAllAgent() {
//        try {
//            List<Agent> list = agentDAO.getAllAgent();
//
//            List<AgentDTO> agentDTOs = new ArrayList<AgentDTO>();
//            AgentDTO agentDTO = null;
//            for (Agent agent : list) {
//                agentDTO = new AgentDTO();
//                agentDTO.setAgentId(agent.getAgentId());
//                agentDTO.setEmail(agent.getEmail());
//                agentDTO.setAgentName(agent.getAgentName());
//                agentDTO.setAddress(agent.getAddress());
//                agentDTO.setStatus(agent.getStatus());
//                // Định dạng dd/MM/yyyy
//                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//                agentDTO.setRegistrationDate(formatter.format(agent.getRegistrationDate()));
//                agentDTO.setAccountBalance(agent.getAccountBalance()+"");
//                agentDTOs.add(agentDTO);
//            }
//            return agentDTOs;
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }

    public boolean createAgent(Agent agent) {
        boolean result = false;
        try {;
            agentDAO.createAgent(agent);
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public boolean updateAgent(int id, Agent agent) {
        boolean result = false;
        try {
            agentDAO.updateAgent(id, agent);
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public boolean deleteAgent(int id) {
        boolean result = false;
        try {
            agentDAO.deleteAgent(id);
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public AgentDTO getAgent(int id) {
        Agent agent = agentDAO.getAgent(id);
        AgentDTO agentDTO = new AgentDTO();
        agentDTO.setAgentId(agent.getAgentId());
        agentDTO.setEmail(agent.getEmail());
        agentDTO.setAgentName(agent.getAgentName());
        agentDTO.setAddress(agent.getAddress());
        agentDTO.setStatus(agent.getStatus());
        // Định dạng dd/MM/yyyy
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        agentDTO.setRegistrationDate(formatter.format(agent.getRegistrationDate()));
        agentDTO.setAccountBalance(agent.getAccountBalance()+"");
        return agentDTO;
    }

//    @Override
//    public List<AgentDTO> getAllAgentPagination(Paging paging) {
//        try {
//            List<Agent> list = agentDAO.getAllAgentPagination(paging);
//
//            List<AgentDTO> agentDTOs = new ArrayList<AgentDTO>();
//            AgentDTO agentDTO = null;
//            for (Agent agent : list) {
//                agentDTO = new AgentDTO();
//                agentDTO.setAgentId(agent.getAgentId());
//                agentDTO.setEmail(agent.getEmail());
//                agentDTO.setAgentName(agent.getAgentName());
//                agentDTO.setAddress(agent.getAddress());
//                agentDTO.setStatus(agent.getStatus());
//                // Định dạng dd/MM/yyyy
//                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//                agentDTO.setRegistrationDate(formatter.format(agent.getRegistrationDate()));
//                agentDTO.setAccountBalance(agent.getAccountBalance()+"");
//                agentDTOs.add(agentDTO);
//            }
//            return agentDTOs;
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }
//
//    @Override
//    public int getTotalRows() {
//        return agentDAO.getTotalRows();
//    }
}
