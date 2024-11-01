package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.TagForNewsDAO;
import com.spring.mvc.entity.News;
import com.spring.mvc.entity.TagForNews;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository(value = "tagForNewsDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class TagForNewsDAOImpl implements TagForNewsDAO {
    private final SessionFactory sessionFactory;
    private Session session;
    public TagForNewsDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        session = sessionFactory.getCurrentSession();
    }

    @Override
    public List<TagForNews> getAllTag() {
        List<TagForNews> list = null;
        TypedQuery<TagForNews> query = session.createQuery("FROM TagForNews", TagForNews.class);
        list = query.getResultList();
        return list;
    }

    @Override
    public TagForNews getTagById(int id) {
        return session.find(TagForNews.class, id);
    }
}
