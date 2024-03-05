package com.bankingsystem.controller;

import com.bankingsystem.entity.UserEntity;
import com.bankingsystem.model.UserCreationData;
import com.bankingsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<UserEntity> createUser(@RequestBody @Valid UserCreationData creationData) {
        UserEntity user = userService.createUser(creationData);
        return ResponseEntity.ok(user);
    }
}
