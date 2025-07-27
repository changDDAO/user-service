package com.changddao.userservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_profiles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserProfile {
    @Id
    private Long userId; //Auth-Service에서 생성 후, 전달됨

    @Column(nullable = false, length = 40)
    private String nickname;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length=20)
    private String phoneNumber;

    private String imageUrl;

    @Embedded
    private Address address;

    public void updateImageUrl(String newImageUrl) {
        this.imageUrl = newImageUrl;
    }
}
