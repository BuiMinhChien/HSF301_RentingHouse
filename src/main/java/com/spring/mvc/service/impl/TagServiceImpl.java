package com.spring.mvc.service.impl;

import com.spring.mvc.dao.TagDAO;
import com.spring.mvc.entity.Tag;
import com.spring.mvc.entity.TagForNews;
import com.spring.mvc.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "tagService")
@Transactional(propagation = Propagation.REQUIRED)
public class TagServiceImpl implements TagService {
    private final TagDAO tagDAO;
    public TagServiceImpl(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }

    @Override
    public List<Tag> getAllTag() {
        try {
            List<Tag> list = tagDAO.getAllTag();
            return list;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public Tag getTagById(int id) {
        try {
            Tag tag = tagDAO.getTagById(id);
            return tag;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
