package com.spring.mvc.service.impl;

import com.spring.mvc.dao.NewsDAO;
import com.spring.mvc.dao.TagForNewsDAO;
import com.spring.mvc.entity.News;
import com.spring.mvc.entity.TagForNews;
import com.spring.mvc.service.NewsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "newsService")
@Transactional(propagation = Propagation.REQUIRED)
public class NewsServiceImpl implements NewsService {
    private final NewsDAO newsDAO;
    public NewsServiceImpl(NewsDAO newsDAO) {
        this.newsDAO = newsDAO;
    }

    @Override
    public boolean save(News news) {
        try {
            newsDAO.save(news);
            return true;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public List<News> getAllNews() {
        try {
            List<News> list = newsDAO.getAllNews();
            return list;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public List<News> getAllNewsByAuthorId(int authorId) {
        try {
            List<News> list = newsDAO.getAllNewsByAuthorId(authorId);
            return list;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public News getNewsById(int id) {
        try {
            News news = newsDAO.getNewsById(id);
            return news;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public boolean deleteNewsById(int id) {
        try {
            newsDAO.deleteNewsById(id);
            return true;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public List<News> getTop3LatestNews() {
        try {
            List<News> list = newsDAO.getTop3LatestNews();
            return list;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
