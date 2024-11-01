package com.spring.mvc.service.impl;

import com.spring.mvc.dao.ImageDAO;
import com.spring.mvc.dao.TagForNewsDAO;
import com.spring.mvc.entity.TagForNews;
import com.spring.mvc.service.TagForNewsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service(value = "tagForNewsService")
@Transactional(propagation = Propagation.REQUIRED)
public class TagForNewsServiceImpl implements TagForNewsService {
    private final TagForNewsDAO tagForNewsDAO;
    public TagForNewsServiceImpl(TagForNewsDAO tagForNewsDAO) {
        this.tagForNewsDAO = tagForNewsDAO;
    }
    @Override
    public List<TagForNews> getAllTag() {
        try {
            List<TagForNews> list = tagForNewsDAO.getAllTag();
            return list;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public TagForNews getTagById(int id) {
        try {
            TagForNews tag = tagForNewsDAO.getTagById(id);
            return tag;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
