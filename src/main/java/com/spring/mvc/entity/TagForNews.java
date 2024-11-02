package com.spring.mvc.entity;

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
@Table(name = "Tag_for_news")
public class TagForNews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", columnDefinition = "NVARCHAR")
    private String name;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<News> newsList;

    @Override
    public String toString() {
        return "TagForNews{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void addNews(News news) {
        if(this.newsList == null){
            this.newsList = new ArrayList<>();
        }
        this.newsList.add(news);
    }
}
