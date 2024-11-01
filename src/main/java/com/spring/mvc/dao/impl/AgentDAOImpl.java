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

}
