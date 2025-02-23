package com.lhg1006.secondhandmarketplaceboot.controller;

import com.lhg1006.secondhandmarketplaceboot.dto.AuthRequestDto;
import com.lhg1006.secondhandmarketplaceboot.dto.AuthResponseDto;
import com.lhg1006.secondhandmarketplaceboot.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.ok(new AuthResponseDto(token, "Bearer"));
    }
}