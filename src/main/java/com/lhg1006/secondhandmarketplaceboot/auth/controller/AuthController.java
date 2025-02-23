package com.lhg1006.secondhandmarketplaceboot.auth.controller;

import com.lhg1006.secondhandmarketplaceboot.auth.dto.AuthRequestDto;
import com.lhg1006.secondhandmarketplaceboot.auth.dto.AuthResponseDto;
import com.lhg1006.secondhandmarketplaceboot.auth.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth API", description = "인증 관련 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody AuthRequestDto.Signup request) {
        authService.registerUser(request);
        return ResponseEntity.ok("회원가입 성공!");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody AuthRequestDto.Login request) {
        String token = authService.authenticateUser(request);
        return ResponseEntity.ok(AuthResponseDto.builder()
                .token(token)
                .tokenType("Bearer")
                .build());
    }
}