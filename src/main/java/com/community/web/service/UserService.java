package com.community.web.service;

import com.community.web.domain.User;
import com.community.web.dto.UserDto;
import com.community.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User toEntity(UserDto userDto){
        return userRepository.findById(userDto.getIdx()).orElse(null);
    }

}
