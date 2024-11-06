package com.spring.mvc.service.impl;

import com.spring.mvc.dao.NotificationDAO;
import com.spring.mvc.dto.NotificationDTO;
import com.spring.mvc.entity.Account;
import com.spring.mvc.entity.Notification;
import com.spring.mvc.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service(value = "notificationService")
@Transactional(propagation = Propagation.REQUIRED)
public class NotificationServiceImpl implements NotificationService {
    private final NotificationDAO notificationDAO;
    private final Map<Integer, SseEmitter> emitters = new ConcurrentHashMap<>();

    public NotificationServiceImpl(NotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }

    @Override
    public NotificationDTO convertToDTO(Notification notification) {
        return new NotificationDTO(notification.getId(),
                notification.getContent(),
                notification.getCreated_date(),
                notification.getRead_status(),
                notification.getHouse().getId());    }

    @Override
    public void saveNotification(Notification notification) {
        notificationDAO.save(notification);
    }

    @Override
    public List<NotificationDTO> getNotificationsForAccount(Account account) {
        List<Notification> notifications = notificationDAO.findByAccountIdOrderByCreatedDateDesc(account.getId());
        return notifications.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void markNotificationAsRead(int notificationId) {
        Notification notification = notificationDAO.findById(notificationId);
        notification.setRead_status("read");
        notificationDAO.save(notification);
    }

    @Override
    public void addEmitter(int clientId, SseEmitter emitter) {
        emitters.put(clientId, emitter);
    }

    @Override
    public void removeEmitter(int clientId) {
        emitters.remove(clientId);
    }

    @Override
    public void sendNotification(Notification notification) {
        NotificationDTO notificationDTO = convertToDTO(notification);

        notification.getAccounts().forEach(account -> {
            SseEmitter emitter = emitters.get(account.getId());
            if (emitter != null) {
                try {
                    emitter.send(SseEmitter.event().name("newNotification").data(notificationDTO));
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }
}
