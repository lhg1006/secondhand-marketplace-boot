package com.lhg1006.secondhandmarketplaceboot.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

public class AuthRequestDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Signup {
        @NotBlank(message = "사용자 이름은 필수 입력 값입니다.")
        @Size(min = 3, max = 20, message = "사용자 이름은 3~20자 사이여야 합니다.")
        private String username;

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Size(min = 6, max = 100, message = "비밀번호는 최소 6자 이상이어야 합니다.")
        private String password;

        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Email(message = "올바른 이메일 형식을 입력해야 합니다.")
        private String email;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Login {
        @NotBlank(message = "사용자 이름을 입력하세요.")
        private String username;

        @NotBlank(message = "비밀번호를 입력하세요.")
        private String password;
    }
}
