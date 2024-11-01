package com.spring.mvc.service.impl;

import com.spring.mvc.dao.ImageDAO;
import com.spring.mvc.entity.Image;
import com.spring.mvc.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "imageService")
@Transactional(propagation = Propagation.REQUIRED)
public class ImageServiceImpl implements ImageService {
    private final ImageDAO imageDAO;
    public ImageServiceImpl(ImageDAO imageDAO) {
        this.imageDAO = imageDAO;
    }

    @Override
    public Image findImageById(int id) {
        try {
            Image image = imageDAO.findImageById(id);
            return image;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public boolean saveImage(Image image) {
        boolean result = false;
        try {
            imageDAO.saveImage(image);
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    public Image getDefaultAvatar() {
        try {
            Image image = imageDAO.getDefaultAvatar();
            return image;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
