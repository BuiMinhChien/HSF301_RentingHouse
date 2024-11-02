package com.spring.mvc.service;

import com.spring.mvc.entity.Question;

import java.util.List;

public interface QuestionService {
    public List<Question> getQuestionsByTopic(int topicid);
    public boolean updateQuestion(int questionId, String newQuestion, String newAnswer);
    public void deleteQuestion(int questionId);
}
