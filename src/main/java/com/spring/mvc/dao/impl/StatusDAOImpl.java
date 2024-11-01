package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.StatusDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository(value = "statusDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class StatusDAOImpl implements StatusDAO {
    private final SessionFactory sessionFactory;
    public StatusDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


}
