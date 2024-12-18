package com.spring.mvc.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="topic_name" , columnDefinition = "NVARCHAR")
    private String topic_name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name="parent_topic_id")
    @JsonBackReference
    private Topic parent_topic;

    @OneToMany(mappedBy = "parent_topic", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JsonManagedReference
    private List<Topic> subTopics;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Question> questions;

    @Override
    public String toString() {
        return "Topic{" +
                "id=" + id +
                ", topic_name='" + topic_name + '\'' +
                ", parent_topic=" + parent_topic +
                '}';
    }

    public void addSubTopic(Topic topic) {
        if(this.subTopics == null){
            this.subTopics = new ArrayList<>();
        }
        this.subTopics.add(topic);
    }

    public void addQuestion(Question question) {
        if (this.questions == null) {
            this.questions = new ArrayList<>();
        }
        this.questions.add(question);
    }
}
