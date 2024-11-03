package com.spring.mvc.service.impl;

import com.spring.mvc.dao.DocumentDAO;
import com.spring.mvc.entity.Document;
import com.spring.mvc.service.DocumentService;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceImpl implements DocumentService {
    private DocumentDAO documentDAO;
    public DocumentServiceImpl(DocumentDAO documentDAO) {
        this.documentDAO = documentDAO;
    }
    @Override
    public void save(Document document) {
        documentDAO.save(document);
    }
}
