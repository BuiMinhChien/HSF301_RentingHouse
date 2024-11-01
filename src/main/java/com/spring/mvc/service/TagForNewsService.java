package com.spring.mvc.service;

import com.spring.mvc.entity.TagForNews;

import java.util.List;

public interface TagForNewsService {
    public List<TagForNews> getAllTag();
    public TagForNews getTagById(int id);
}
