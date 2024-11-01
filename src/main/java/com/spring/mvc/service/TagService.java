package com.spring.mvc.service;

import com.spring.mvc.entity.Tag;

import java.util.List;

public interface TagService {
    public List<Tag> getAllTag();
    public Tag getTagById(int id);
}
