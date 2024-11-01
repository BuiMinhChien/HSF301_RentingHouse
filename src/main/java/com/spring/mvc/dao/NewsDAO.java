package com.spring.mvc.dao;

import com.spring.mvc.entity.News;
import java.util.List;

public interface NewsDAO {
    public void save(News news);
    public List<News> getAllNews();
    public List<News> getAllNewsByAuthorId(int authorId);
    public News getNewsById(int id);
    public void deleteNewsById(int id);
    public List<News> getTop3LatestNews();
}
