package com.lhg1006.secondhandmarketplaceboot.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileResponseDto {
    private String username;
    private String location;
    private String bio;
    private String avatarUrl;
}