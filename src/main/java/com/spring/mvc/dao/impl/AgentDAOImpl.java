package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.AgentDAO;
import com.spring.mvc.entity.Paging;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository(value = "agentDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class AgentDAOImpl implements AgentDAO {
    private final SessionFactory sessionFactory;
    public AgentDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Agent> findByAccountNameStatus(String account, String name, String status, Paging paging) {
        Session session = sessionFactory.getCurrentSession();
        StringBuilder queryString = new StringBuilder("FROM Agent WHERE 1=1");
        if (account != null && !account.isEmpty()) {
            queryString.append(" AND email LIKE :account");
        }
        if (name != null && !name.isEmpty()) {
            queryString.append(" AND agentName LIKE :name");
        }
        if (status != null && !status.isEmpty() && Integer.parseInt(status) != -1) {
            queryString.append(" AND status.id = :status");
        }
        queryString.append(" ORDER BY registrationDate ASC, agentName ASC");

        TypedQuery<Agent> query = session.createQuery(queryString.toString(), Agent.class);

        if (account != null && !account.isEmpty()) {
            query.setParameter("account", "%" + account + "%");
        }
        if (name != null && !name.isEmpty()) {
            query.setParameter("name", "%" + name + "%");
        }
        if (status != null && !status.isEmpty() && Integer.parseInt(status) != -1) {
            query.setParameter("status", Integer.parseInt(status));
        }
        List<Agent> agents = null;
        if(paging!=null){
            paging.setTotalRows(query.getResultList().size());
            query.setFirstResult(paging.getOffset());
            query.setMaxResults(paging.getPageSize());
            agents = query.getResultList();
        }
        return agents;
    }

    @Override
    public int getTotalRowsForSearch(String account, String name, String status) {
        Session session = sessionFactory.getCurrentSession();
        StringBuilder queryString = new StringBuilder("FROM Agent WHERE 1=1");
        if (account != null && !account.isEmpty()) {
            queryString.append(" AND email LIKE :account");
        }
        if (name != null && !name.isEmpty()) {
            queryString.append(" AND agentName LIKE :name");
        }
        if (status != null && !status.isEmpty() && Integer.parseInt(status) != -1) {
            queryString.append(" AND status.id = :status");
        }
        queryString.append(" ORDER BY registrationDate ASC, agentName ASC");

        TypedQuery<Agent> query = session.createQuery(queryString.toString(), Agent.class);

        if (account != null && !account.isEmpty()) {
            query.setParameter("account", "%" + account + "%");
        }
        if (name != null && !name.isEmpty()) {
            query.setParameter("name", "%" + name + "%");
        }
        if (status != null && !status.isEmpty() && Integer.parseInt(status) != -1) {
            query.setParameter("status", Integer.parseInt(status));
        }
        return query.getResultList().size();
    }


    @Override
    public List<Agent> getAllAgent() {
        List<Agent> agents = null;
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Agent> query = session.createQuery("FROM Agent ORDER BY registrationDate ASC, agentName ASC", Agent.class);
        agents = query.getResultList();
        return agents;
    }

    @Override
    public List<Agent> getAllAgentPagination(Paging paging) {
        List<Agent> agents = null;
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Agent> query = session.createQuery("FROM Agent ORDER BY registrationDate ASC, agentName ASC", Agent.class);
        if(paging!=null){
            paging.setTotalRows(query.getResultList().size());
            query.setFirstResult(paging.getOffset());
            query.setMaxResults(paging.getPageSize());
            agents = query.getResultList();
        }
        return agents;
    }
//    @Override
//    public int getTotalRows() {
//        Session session = sessionFactory.getCurrentSession();
//        TypedQuery<Agent> query = session.createQuery("FROM Agent", Agent.class);
//        return query.getResultList().size();
//    }

    @Override
    public void createAgent(Agent agent) {
        Session session = sessionFactory.getCurrentSession();
        session.save(agent);
    }

    @Override
    public void updateAgent(int id, Agent agent) {
        Session session = sessionFactory.getCurrentSession();
        System.out.println("ID: " + id);
        System.out.println("Infor: " + agent.toString());
        Agent updatedAgent = session.find(Agent.class, id);
        System.out.println("Finded agent: " + updatedAgent.toString());
        updatedAgent.setAgentName(agent.getAgentName());
        updatedAgent.setAddress(agent.getAddress());
        updatedAgent.setEmail(agent.getEmail());
        updatedAgent.setAccountBalance(agent.getAccountBalance());
        updatedAgent.setRegistrationDate(agent.getRegistrationDate());
        updatedAgent.setStatus(agent.getStatus());
        System.out.println("Changed agent: " + updatedAgent.toString());
        session.merge(updatedAgent);
    }

    @Override
    public Agent getAgent(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(Agent.class, id);
    }

    @Override
    public void deleteAgent(int id) {
        Session session = sessionFactory.getCurrentSession();
        Agent agent = session.find(Agent.class, id);
        session.remove(agent);
    }

}
