package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.TagDAO;
import com.spring.mvc.entity.Tag;
import com.spring.mvc.entity.TagForNews;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository(value = "tagDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class TagDAOImpl implements TagDAO {
    private final SessionFactory sessionFactory;
//    private Session session;
    public TagDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
//        session = sessionFactory.getCurrentSession();
    }

    @Override
    public List<Tag> getAllTag() {
        List<Tag> list = null;
        TypedQuery<Tag> query = sessionFactory.getCurrentSession().createQuery("FROM Tag", Tag.class);
        list = query.getResultList();
        return list;
    }

    @Override
    public Tag getTagById(int id) {
        return sessionFactory.getCurrentSession().find(Tag.class, id);
    }
}
