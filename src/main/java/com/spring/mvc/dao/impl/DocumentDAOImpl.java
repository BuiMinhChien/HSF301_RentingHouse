package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.DocumentDAO;
import com.spring.mvc.entity.Document;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository(value = "documentDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class DocumentDAOImpl implements DocumentDAO {
    private SessionFactory sessionFactory;

    public DocumentDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Document document) {
        Session session = sessionFactory.getCurrentSession();
       session.save(document);
    }
}
