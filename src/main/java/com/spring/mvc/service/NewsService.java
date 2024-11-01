package com.spring.mvc.service;

import com.spring.mvc.entity.News;

import java.util.List;

public interface NewsService {
    public boolean save(News news);
    public List<News> getAllNews();
    public List<News> getAllNewsByAuthorId(int authorId);
    public News getNewsById(int id);
    public boolean deleteNewsById(int id);
    public List<News> getTop3LatestNews();
}
