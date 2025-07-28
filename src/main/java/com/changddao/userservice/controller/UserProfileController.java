package com.changddao.userservice.controller;

import com.changddao.userservice.dto.CreateUserProfileRequest;
import com.changddao.userservice.exception.AccessDeniedException;
import com.changddao.userservice.service.UserProfileService;
import com.changddao.userservice.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService userProfileService;
    private final JwtUtil jwtUtil;

    @PostMapping("/internal/users")
    public ResponseEntity<Void> createUserProfile(@RequestBody CreateUserProfileRequest req) {
        userProfileService.createProfile(req);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/users/{id}/image")
    public ResponseEntity<Void> updateProfileImage(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader,
            @RequestParam("image") MultipartFile image
    ) {
        String token = authHeader.replace("Bearer ", "");
        String tokenUserId = jwtUtil.getUserIdFromToken(token);
        if (!id.toString().equals(tokenUserId)) {
            throw new AccessDeniedException("본인만 프로필 수정이 가능합니다.");
        }
        userProfileService.updateProfileImage(id, image);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{id}/image")
    public ResponseEntity<Void> deleteProfileImage(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String tokenUserId = jwtUtil.getUserIdFromToken(token);
        if (!id.toString().equals(tokenUserId)) {
            throw new AccessDeniedException("본인만 프로필 삭제가 가능합니다.");
        }
        userProfileService.deleteProfileImage(id);
        return ResponseEntity.ok().build();
    }

}
