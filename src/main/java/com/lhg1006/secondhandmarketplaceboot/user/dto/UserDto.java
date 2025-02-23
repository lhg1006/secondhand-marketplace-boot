package com.lhg1006.secondhandmarketplaceboot.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String location;
    private String bio;
    private String avatarUrl;
}