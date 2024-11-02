package com.spring.mvc.service;

import com.spring.mvc.dto.NotificationDTO;
import com.spring.mvc.entity.Account;
import com.spring.mvc.entity.Notification;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface NotificationService {
    public NotificationDTO convertToDTO(Notification notification);
    public void saveNotification(Notification notification);
    public List<NotificationDTO> getNotificationsForAccount(Account account);
    public void markNotificationAsRead(int notificationId);
    public void addEmitter(int clientId, SseEmitter emitter);
    public void removeEmitter(int clientId);
    public void sendNotification(Notification notification);
}
