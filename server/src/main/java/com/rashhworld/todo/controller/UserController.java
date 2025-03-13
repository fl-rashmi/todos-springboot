package com.rashhworld.todo.controller;

import com.rashhworld.todo.SpringResponse;
import com.rashhworld.todo.entity.UserEntity;
import com.rashhworld.todo.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<SpringResponse> createUser(@Valid @RequestBody UserEntity data) {
        try {
            userService.registerUser(data);
            SpringResponse response = SpringResponse.builder().status("success").message("Registration successful.").build();
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            SpringResponse response = SpringResponse.builder().status("error").message(e.getMessage()).build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<SpringResponse> loginUser(@Valid @RequestBody UserEntity data) {
        try {
            String token = userService.loginUser(data);
            SpringResponse response = SpringResponse.builder().status("success").message("Login successful.").data(token).build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            SpringResponse response = SpringResponse.builder().status("error").message(e.getMessage()).build();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
