package com.lhg1006.secondhandmarketplaceboot.controller;

import com.lhg1006.secondhandmarketplaceboot.dto.*;
import com.lhg1006.secondhandmarketplaceboot.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;
import java.util.List;

@Tag(name = "User API", description = "유저 관련 API")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponseDto> getUserProfile(Authentication authentication) {
        return ResponseEntity.ok(userService.getUserProfile(authentication.getName()));
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateUserProfile(@Valid @RequestBody UserProfileRequestDto requestDto,
                                                  Authentication authentication) {
        userService.updateUserProfile(authentication.getName(), requestDto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/me/avatar")
    public ResponseEntity<Void> uploadProfileImage(@RequestParam("file") MultipartFile file,
                                                   Authentication authentication) throws IOException {
        userService.uploadProfileImage(authentication.getName(), file);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody UserProfileRequestDto requestDto) {
        userService.updateUserProfile(id, requestDto);
        return ResponseEntity.ok("유저 정보가 업데이트되었습니다.");
    }

    @PutMapping("/me/password")
    public ResponseEntity<String> changePassword(
            @RequestBody UserPasswordRequestDto requestDto,
            Authentication authentication) {

        userService.changePassword(authentication.getName(), requestDto);
        return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
    }

}
