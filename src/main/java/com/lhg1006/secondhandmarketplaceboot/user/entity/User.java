package com.lhg1006.secondhandmarketplaceboot.user.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private String location;  // 위치 정보 추가
    private String bio;       // 자기소개 추가
    private String avatarUrl; // 프로필 이미지 URL 추가
}