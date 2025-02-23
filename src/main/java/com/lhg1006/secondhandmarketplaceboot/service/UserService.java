package com.lhg1006.secondhandmarketplaceboot.service;

import com.lhg1006.secondhandmarketplaceboot.dto.UserDto;
import com.lhg1006.secondhandmarketplaceboot.entity.User;
import com.lhg1006.secondhandmarketplaceboot.exception.CustomException;
import com.lhg1006.secondhandmarketplaceboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("유저를 찾을 수 없습니다.", 404));

        return new UserDto(user);
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserDto::new).collect(Collectors.toList());
    }
}