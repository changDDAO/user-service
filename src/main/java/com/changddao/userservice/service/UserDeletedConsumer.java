package com.changddao.userservice.service;

import com.changddao.auth_service.dto.kafka.UserDeletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDeletedConsumer {
    private final UserProfileService userProfileService;

    @KafkaListener(topics="user.deleted", groupId = "user-event-handler")
    public void consume(UserDeletedEvent event) {
        log.info("Kafka user.deleted event 수신: {}", event);
        userProfileService.deleteUserProfile(event.getUserId());
    }
}
