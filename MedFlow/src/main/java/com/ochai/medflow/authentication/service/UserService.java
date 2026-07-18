package com.ochai.medflow.authentication.service;

import com.ochai.medflow.authentication.entity.User;
import com.ochai.medflow.authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public List<User> findAll() {
        return userRepository.findAll();
    }

}
