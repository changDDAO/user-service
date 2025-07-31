package com.changddao.auth_service.dto.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDeletedEvent {
    private Long userId;
    private String email;
    private String reason;
}
