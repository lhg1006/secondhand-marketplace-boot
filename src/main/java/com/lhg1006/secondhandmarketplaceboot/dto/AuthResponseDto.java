package com.lhg1006.secondhandmarketplaceboot.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDto {
    private String token;

    @Builder.Default
    private String tokenType = "Bearer";
}