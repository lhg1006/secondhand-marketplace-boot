package com.lhg1006.secondhandmarketplaceboot.auth.service;

import com.lhg1006.secondhandmarketplaceboot.security.JwtTokenProvider;
import com.lhg1006.secondhandmarketplaceboot.auth.dto.AuthRequestDto;
import com.lhg1006.secondhandmarketplaceboot.user.entity.User;
import com.lhg1006.secondhandmarketplaceboot.exception.CustomException;
import com.lhg1006.secondhandmarketplaceboot.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입 메서드
    public void registerUser(AuthRequestDto.Signup request) {
        // 중복된 사용자명(username) 검사
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomException(400, "이미 존재하는 사용자입니다.");
        }

        // 비밀번호 암호화 후 저장
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // User 엔티티 생성 및 저장
        User user = User.builder()
                .username(request.getUsername())
                .password(encodedPassword) // 암호화된 비밀번호 저장
                .email(request.getEmail())
                .build();

        userRepository.save(user);
    }

    // 사용자 로그인 및 인증 메서드
    public String authenticateUser(AuthRequestDto.Login request) {
        // 사용자명(username)으로 유저 검색 (없으면 예외 발생)
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));

        // 입력된 비밀번호가 저장된 비밀번호와 일치하는지 확인
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(401, "잘못된 비밀번호입니다.");
        }

        // JWT 토큰 생성 후 반환
        return jwtTokenProvider.createToken(user.getUsername());
    }
}
