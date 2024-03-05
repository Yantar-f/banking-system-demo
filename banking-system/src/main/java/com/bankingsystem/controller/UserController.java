package com.bankingsystem.controller;

import com.bankingsystem.model.UserDTO;
import com.bankingsystem.model.UserSearchCriteria;
import com.bankingsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Page<UserDTO>> getUsers(@Valid  UserSearchCriteria searchCriteria) {
        Page<UserDTO> users = userService.getUsersBy(searchCriteria).map(UserDTO::new);
        return ResponseEntity.ok(users);
    }
}
