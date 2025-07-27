package com.changddao.userservice.service;

import com.changddao.userservice.dto.CreateUserProfileRequest;
import com.changddao.userservice.entity.Address;
import com.changddao.userservice.entity.UserProfile;
import com.changddao.userservice.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;

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

}
