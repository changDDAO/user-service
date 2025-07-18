package com.changddao.userservice.controller;

import com.changddao.userservice.dto.CreateUserProfileRequest;
import com.changddao.userservice.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/users")
public class UserProfileController {
    private final UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<Void> createUserProfile(@RequestBody CreateUserProfileRequest req) {
        userProfileService.createProfile(req);
        return ResponseEntity.ok().build();
    }
}
