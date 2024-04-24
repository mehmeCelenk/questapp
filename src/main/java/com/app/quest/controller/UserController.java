package com.app.quest.controller;

import com.app.quest.model.request.UserCreateRequest;
import com.app.quest.model.request.UserUpdateRequest;
import com.app.quest.model.response.UserResponse;
import com.app.quest.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/questapp/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreateRequest userCreate) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userCreate));
    }

    @PutMapping()
    @Transactional
    public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UserUpdateRequest userUpdate) {
        return ResponseEntity.ok(userService.update(userUpdate));
    }

    @GetMapping("{uuid}")
    public ResponseEntity<UserResponse> getUserId(String uuid) {
        return ResponseEntity.ok(userService.getByUser(uuid));
    }

    @DeleteMapping("{id}")
    public void delete(String uuid) {
        userService.delete(uuid);
    }

}
