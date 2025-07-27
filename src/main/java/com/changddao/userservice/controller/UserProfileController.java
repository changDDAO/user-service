package com.changddao.userservice.controller;

import com.changddao.userservice.dto.CreateUserProfileRequest;
import com.changddao.userservice.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;

    @PostMapping("/internal/users")
    public ResponseEntity<Void> createUserProfile(@RequestBody CreateUserProfileRequest req) {
        userProfileService.createProfile(req);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{id}/image")
    public ResponseEntity<Void> updateProfileImage(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile image
    ) {
        userProfileService.updateProfileImage(id, image);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{id}/image")
    public ResponseEntity<Void> deleteProfileImage(
            @PathVariable Long id) {
        userProfileService.deleteProfileImage(id);
        return ResponseEntity.ok().build();
    }

}
