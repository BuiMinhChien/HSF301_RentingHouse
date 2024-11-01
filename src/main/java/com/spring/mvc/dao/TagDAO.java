package com.spring.mvc.dao;

import com.spring.mvc.entity.Tag;

import java.util.List;

public interface TagDAO {
    public List<Tag> getAllTag();
    public Tag getTagById(int id);
}
