package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.NewsDAO;
import com.spring.mvc.entity.News;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository(value = "newsDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class NewsDAOIpml implements NewsDAO {
    private final SessionFactory sessionFactory;
//    private Session session;
    public NewsDAOIpml(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
//        session = sessionFactory.getCurrentSession();
    }

    @Override
    public void save(News news) {
        sessionFactory.getCurrentSession().save(news);
    }

    @Override
    public List<News> getAllNews() {
        List<News> list = null;
        TypedQuery<News> query = sessionFactory.getCurrentSession().createQuery("FROM News ORDER BY registrationDate DESC ", News.class);
        list = query.getResultList();
        return list;
    }

    @Override
    public List<News> getAllNewsByAuthorId(int authorId) {
        List<News> list = null;
        TypedQuery<News> query = sessionFactory.getCurrentSession().createQuery("FROM News WHERE account.id = :authorId ORDER BY registrationDate DESC ", News.class);
        query.setParameter("authorId", authorId);
        list = query.getResultList();
        return list;
    }

    @Override
    public News getNewsById(int id) {
        return sessionFactory.getCurrentSession().find(News.class, id);
    }

    @Override
    public void deleteNewsById(int id) {
        News news = sessionFactory.getCurrentSession().find(News.class, id);
        sessionFactory.getCurrentSession().remove(news);
    }

    @Override
    public List<News> getTop3LatestNews() {
        List<News> list = null;
        TypedQuery<News> query = sessionFactory.getCurrentSession().createQuery("FROM News ORDER BY registrationDate DESC", News.class);
        query.setMaxResults(3); // Giới hạn kết quả chỉ lấy 3 bản ghi gần nhất
        list = query.getResultList();
        return list;
    }
}
