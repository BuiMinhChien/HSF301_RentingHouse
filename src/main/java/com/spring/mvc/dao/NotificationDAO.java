package com.spring.mvc.dao;

import com.spring.mvc.entity.Notification;
import java.util.List;

public interface NotificationDAO {
    public void save(Notification notification);
    public List<Notification> findByAccountIdOrderByCreatedDateDesc(int accountId);
    Notification findById(int id);
}
