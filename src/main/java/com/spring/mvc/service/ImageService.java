package com.spring.mvc.service;

import com.spring.mvc.entity.Image;

public interface ImageService {
    public Image findImageById(int id);
    public boolean saveImage(Image image);
    public Image getDefaultAvatar();
}
