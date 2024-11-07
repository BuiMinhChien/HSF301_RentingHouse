package com.spring.mvc.service.impl;

import com.spring.mvc.dao.QuestionDAO;
import com.spring.mvc.dao.TopicDAO;
import com.spring.mvc.entity.Question;
import com.spring.mvc.entity.Topic;
import com.spring.mvc.service.QuestionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "questionService")
@Transactional(propagation = Propagation.REQUIRED)
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDAO questionDAO;
    private final TopicDAO topicDAO;

    public QuestionServiceImpl(QuestionDAO questionDAO, TopicDAO topicDAO) {
        this.questionDAO = questionDAO;
        this.topicDAO = topicDAO;
    }

    @Override
    public List<Question> getQuestionsByTopic(int topicid) {
        Topic topic = topicDAO.findById(topicid);
        return questionDAO.findByTopic(topic);
    }

    @Override
    public boolean updateQuestion(int questionId, String newQuestion, String newAnswer) {
        Question question = questionDAO.findById(questionId);
        if (question != null) {
            question.setQuestion(newQuestion);
            question.setAnswer(newAnswer);
            questionDAO.save(question);
            return true;
        }
        return false;
    }

    @Override
    public void deleteQuestion(int questionId) {
        questionDAO.deleteQuestionById(questionId);
    }

    @Override
    public boolean saveQuestion(int subTopicId, String newQuestion, String newAnswer) {
        try {
            Question questionEntity = new Question();
            Topic topic = topicDAO.findById(subTopicId);
            questionEntity.setTopic(topic);
            questionEntity.setQuestion(newQuestion);
            questionEntity.setAnswer(newAnswer);
            questionDAO.save(questionEntity);
            topic.addQuestion(questionEntity);
            topicDAO.save(topic);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
