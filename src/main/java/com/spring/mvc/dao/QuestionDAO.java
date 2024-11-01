package com.spring.mvc.dao;

import com.spring.mvc.entity.Question;
import com.spring.mvc.entity.Topic;

import java.util.List;

public interface QuestionDAO {
    public void save(Question question);
    public void deleteQuestionById(int questionId);
    public Question findById(int questionId);
    public List<Question> findByTopic(Topic topic);
}
