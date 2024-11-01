package com.spring.mvc.dao.impl;

import com.spring.mvc.dao.QuestionDAO;
import com.spring.mvc.entity.Question;
import com.spring.mvc.entity.Topic;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository(value = "questionDAO")
@Transactional(propagation = Propagation.REQUIRED)
@DependsOn(value = "sessionFactory")
public class QuestionDAOImpl implements QuestionDAO {
    private final SessionFactory sessionFactory;
    private Session session;

    public QuestionDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        session = sessionFactory.getCurrentSession();
    }

    @Override
    public void save(Question question) {
        session.save(question);
    }

    @Override
    public void deleteQuestionById(int questionId) {
        Question question = findById(questionId);
        session.remove(question);
    }

    @Override
    public Question findById(int questionId) {
        return session.find(Question.class, questionId);
    }

    @Override
    public List<Question> findByTopic(Topic topic) {
        String hql = "FROM Question q WHERE q.topic = :topic";
        return session.createQuery(hql, Question.class)
                .setParameter("topic", topic)
                .getResultList();
    }
}
