package com.rashhworld.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rashhworld.todo.entity.UserEntity;
import com.rashhworld.todo.repository.UserRepository;
import com.rashhworld.todo.utils.JwtUtil;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void registerUser(UserEntity data) {
        if (userRepository.findByUsername(data.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        data.setPassword(passwordEncoder.encode(data.getPassword()));
        userRepository.save(data);
    }

    public String loginUser(UserEntity data) {
        UserEntity user = userRepository.findByUsername(data.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(data.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return jwtUtil.generateToken(data.getUsername());
    }
}
