package com.lhg1006.secondhandmarketplaceboot.auth.dto;

import lombok.*;

@Getter
@Builder
public class AuthResponseDto {
    private String token;

    @Builder.Default
    private String tokenType = "Bearer";
}
