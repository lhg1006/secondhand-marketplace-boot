package com.lhg1006.secondhandmarketplaceboot.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordRequestDto {
    @NotBlank(message = "현재 비밀번호를 입력하세요.")
    private String currentPassword;

    @NotBlank(message = "새로운 비밀번호를 입력하세요.")
    private String newPassword;
}