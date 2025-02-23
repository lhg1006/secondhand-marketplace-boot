package com.lhg1006.secondhandmarketplaceboot.service;

import com.lhg1006.secondhandmarketplaceboot.config.JwtTokenProvider;
import com.lhg1006.secondhandmarketplaceboot.dto.AuthRequestDto;
import com.lhg1006.secondhandmarketplaceboot.entity.User;
import com.lhg1006.secondhandmarketplaceboot.repository.UserRepository;
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

    public void registerUser(AuthRequestDto.Signup request) {
        // 중복된 username 검사
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자 이름입니다.");
        }

        // 비밀번호 암호화 후 저장
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .username(request.getUsername())
                .password(encodedPassword) // 암호화된 비밀번호 저장
                .email(request.getEmail())
                .build();

        userRepository.save(user);
    }

    public String authenticateUser(AuthRequestDto.Login request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return jwtTokenProvider.createToken(user.getUsername());
    }

}