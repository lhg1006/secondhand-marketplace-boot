package com.lhg1006.secondhandmarketplaceboot.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserProfileRequestDto {
    @NotBlank(message = "이름을 입력하세요.")
    private String username;  // 사용자 이름
    private String location;  // 위치 정보
    private String bio;       // 자기소개
    private String avatarUrl; // 아바타
}
