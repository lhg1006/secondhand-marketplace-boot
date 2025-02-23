package com.lhg1006.secondhandmarketplaceboot.user.service;

import com.lhg1006.secondhandmarketplaceboot.user.entity.User;
import com.lhg1006.secondhandmarketplaceboot.exception.CustomException;
import com.lhg1006.secondhandmarketplaceboot.user.repository.UserRepository;
import com.lhg1006.secondhandmarketplaceboot.user.dto.UserDto;
import com.lhg1006.secondhandmarketplaceboot.user.dto.UserPasswordRequestDto;
import com.lhg1006.secondhandmarketplaceboot.user.dto.UserProfileRequestDto;
import com.lhg1006.secondhandmarketplaceboot.user.dto.UserProfileResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 특정 ID로 사용자 조회
    public UserDto getUserById(Long id) {
        User user = findUserById(id);
        return convertToDto(user);
    }

    // 모든 사용자 조회
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // ID로 사용자 찾기 (존재하지 않으면 예외 발생)
    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(404, "해당 ID의 사용자를 찾을 수 없습니다."));
    }

    // Username으로 사용자 찾기 (존재하지 않으면 예외 발생)
    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(404, "사용자를 찾을 수 없습니다."));
    }

    // 현재 로그인한 사용자의 프로필 조회
    public UserProfileResponseDto getUserProfile(String username) {
        User user = findUserByUsername(username);
        return convertToProfileResponse(user);
    }

    // ID 기반 프로필 업데이트
    public void updateUserProfile(Long id, UserProfileRequestDto requestDto) {
        User user = findUserById(id);
        updateUserFields(user, requestDto);
    }

    // Username 기반 프로필 업데이트
    public void updateUserProfile(String username, UserProfileRequestDto requestDto) {
        User user = findUserByUsername(username);
        updateUserFields(user, requestDto);
    }

    // 사용자 정보 업데이트 (공통 메서드)
    private void updateUserFields(User user, UserProfileRequestDto requestDto) {
        user.setUsername(requestDto.getUsername());
        user.setLocation(requestDto.getLocation());
        user.setBio(requestDto.getBio());
        user.setAvatarUrl(requestDto.getAvatarUrl());
        userRepository.save(user);
    }

    // 프로필 이미지 업로드
    public void uploadProfileImage(String username, MultipartFile file) throws IOException {
        User user = findUserByUsername(username);
        String uploadedImageUrl = saveImage(file);
        user.setAvatarUrl(uploadedImageUrl);
        userRepository.save(user);
    }

    // 이미지 저장 로직 (실제 구현 필요)
    private String saveImage(MultipartFile file) throws IOException {
        // TODO: 실제 이미지 저장 로직 추가 (AWS S3, 로컬 저장소 등)
        return "https://example.com/images/" + file.getOriginalFilename();
    }

    // 비밀번호 변경
    public void changePassword(String username, UserPasswordRequestDto requestDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(404, "사용자를 찾을 수 없습니다."));

        // 현재 비밀번호가 맞는지 검증
        if (!passwordEncoder.matches(requestDto.getCurrentPassword(), user.getPassword())) {
            throw new CustomException(400, "현재 비밀번호가 일치하지 않습니다.");
        }

        // 새 비밀번호가 기존 비밀번호와 동일한지 확인
        if (requestDto.getCurrentPassword().equals(requestDto.getNewPassword())) {
            throw new CustomException(400, "새로운 비밀번호는 현재 비밀번호와 다르게 설정해야 합니다.");
        }

        // 비밀번호 암호화 후 저장
        user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
        userRepository.save(user);
    }

    // 사용자 엔티티를 DTO로 변환
    private UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .location(user.getLocation())
                .bio(user.getBio())
                .avatarUrl(user.getAvatarUrl())
                .build();
    }

    // 사용자 엔티티를 프로필 응답 DTO로 변환
    private UserProfileResponseDto convertToProfileResponse(User user) {
        return UserProfileResponseDto.builder()
                .username(user.getUsername())
                .location(user.getLocation())
                .bio(user.getBio())
                .avatarUrl(user.getAvatarUrl())
                .build();
    }
}
