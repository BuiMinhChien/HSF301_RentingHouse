package com.spring.mvc.dao;

import com.spring.mvc.entity.TagForNews;

import java.util.List;

public interface TagForNewsDAO {
    public List<TagForNews> getAllTag();
    public TagForNews getTagById(int id);
}
