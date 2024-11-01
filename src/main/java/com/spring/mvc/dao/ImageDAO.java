package com.spring.mvc.dao;

import com.spring.mvc.entity.Image;

public interface ImageDAO {
    public Image findImageById(int id);
    public void saveImage(Image image);
    public Image getDefaultAvatar();
}
