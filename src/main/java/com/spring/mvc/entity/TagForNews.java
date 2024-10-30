package com.spring.mvc.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "Tag_for_news")
public class TagForNews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    public TagForNews() {
    }

    @Override
    public String toString() {
        return "TagForNews{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
