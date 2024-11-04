package com.spring.mvc.controller;


import com.spring.mvc.dto.NotificationDTO;
import com.spring.mvc.entity.Account;
import com.spring.mvc.service.AccountService;
import com.spring.mvc.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final AccountService accountService;

    public NotificationController(NotificationService notificationService, AccountService accountService) {
        this.notificationService = notificationService;
        this.accountService = accountService;
    }

    // Endpoint lấy danh sách thông báo cho một account
    @GetMapping("/get")
    public List<NotificationDTO> getNotifications(@RequestParam("clientId") int clientId) {
        Account account = accountService.findById(clientId);
        return notificationService.getNotificationsForAccount(account);
    }

    @PostMapping("/markAsRead")
    public ResponseEntity<?> markAsRead(@RequestBody Map<String, Integer> payload) {
        int notificationId = payload.get("notificationId");
        notificationService.markNotificationAsRead(notificationId);
        return ResponseEntity.ok().build();
    }


    // Stream thông báo mới theo thời gian thực bằng SSE
    @GetMapping("/stream/{clientId}")
    public SseEmitter streamNotifications(@PathVariable("clientId") int clientId) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        notificationService.addEmitter(clientId, emitter);

        emitter.onCompletion(() -> notificationService.removeEmitter(clientId));
        emitter.onTimeout(() -> notificationService.removeEmitter(clientId));

        return emitter;
    }
}
