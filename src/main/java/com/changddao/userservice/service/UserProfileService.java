package com.changddao.userservice.service;

import com.changddao.userservice.dto.CreateUserProfileRequest;
import com.changddao.userservice.entity.Address;
import com.changddao.userservice.entity.UserProfile;
import com.changddao.userservice.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final FileService fileService;

    @Value("${minio.bucket}")
    private String bucketName;

    @Value("${minio.url}")
    private String minioUrl;


    @Transactional
    public void createProfile(CreateUserProfileRequest req) {
        Address address = Address.builder()
                .city(req.address().city())
                .street(req.address().street())
                .zipcode(req.address().zipcode())
                .build();

        UserProfile userProfile = UserProfile.builder()
                .userId(req.userId())
                .nickname(req.nickname())
                .name(req.name())
                .phoneNumber(req.phoneNumber())
                .imageUrl(req.imageUrl())
                .address(address)
                .build();

        userProfileRepository.save(userProfile);
    }

    @Transactional
    public void updateProfileImage(Long userId, MultipartFile newImage) {
        UserProfile user = userProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));

        // 1. 기존 이미지 삭제
        if (user.getImageUrl() != null && !user.getImageUrl().isEmpty()) {
            String objectName = extractObjectName(user.getImageUrl());
            fileService.deleteFile(objectName);
        }

        // 2. 새 이미지 업로드
        String objectName = fileService.uploadFile(newImage, "user-profile");
        String newImageUrl = String.format("%s/%s/%s", minioUrl, bucketName, objectName);

        // 3. DB 업데이트
        user.updateImageUrl(newImageUrl);
    }

    @Transactional
    public void deleteProfileImage(Long userId) {
        UserProfile user = userProfileRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 정보를 찾을 수 없습니다."));
        String objectName = extractObjectName(user.getImageUrl());
        fileService.deleteFile(objectName);
        user.updateImageUrl(null);
    }

    private String extractObjectName(String imageUrl) {
        // 예: http://localhost:9000/changhome/user-profile/abc.jpg
        try {
            URI uri = new URI(imageUrl);
            String[] parts = uri.getPath().split("/", 3); // ["", "changhome", "user-profile/abc.jpg"]
            return parts.length >= 3 ? parts[2] : "";
        } catch (Exception e) {
            throw new RuntimeException("이미지 경로 추출 실패", e);
        }
    }
}




