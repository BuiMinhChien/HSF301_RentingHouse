package com.spring.mvc.dao;

import com.spring.mvc.entity.Topic;

import java.util.List;

public interface TopicDAO {
    Topic findById(int topicId);

    void save(Topic topic);

    void deleteById(int topicId);

    // Tìm các topic chính (parent_topic_id = NULL)
    List<Topic> findByParentTopicIsNull();

    // Tìm các sub-topic theo parent_topic_id
    List<Topic> findByParentTopic_TopicId(int parentId);
}
