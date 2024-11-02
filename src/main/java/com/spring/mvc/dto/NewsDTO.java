package com.spring.mvc.dto;

import com.spring.mvc.entity.Account;
import com.spring.mvc.entity.Image;
import com.spring.mvc.entity.News;
import com.spring.mvc.entity.TagForNews;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class NewsDTO {
    private int id;
    private String title;
    private String content;
    private String created_date;
    private Image images;
    private Account account;
    private List<TagForNews> tags;

    public News getNews(){
        News news = new News();
        news.setId(id);
        news.setTitle(title);
        news.setContent(content);
        news.setCreated_date(created_date);
        news.setImages(images);
        news.setAccount(account);
        news.setTags(tags);
        return news;
    }
}