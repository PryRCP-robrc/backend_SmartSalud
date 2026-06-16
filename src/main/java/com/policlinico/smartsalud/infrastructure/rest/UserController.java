package com.policlinico.smartsalud.infrastructure.adapters.input.rest;

import com.policlinico.smartsalud.application.ports.input.UserUseCase;
import com.policlinico.smartsalud.domain.model.User;
import com.policlinico.smartsalud.shared.dto.request.UserRequest;
import com.policlinico.smartsalud.shared.dto.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*") 
public class UserController {

    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        User userModel = new User(null, request.getUsername(), request.getEmail(), request.getPassword(), request.getRole(), true);
        User savedUser = userUseCase.registerUser(userModel);
        UserResponse response = new UserResponse(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail(), savedUser.getRole(), savedUser.isActive());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        User user = userUseCase.getUserById(id);
        UserResponse response = new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.isActive());
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> response = userUseCase.getAllUsers().stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRole(), user.isActive()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
        User userModel = new User(null, request.getUsername(), request.getEmail(), null, request.getRole(), true);
        User updatedUser = userUseCase.updateUser(id, userModel);
        UserResponse response = new UserResponse(updatedUser.getId(), updatedUser.getUsername(), updatedUser.getEmail(), updatedUser.getRole(), updatedUser.isActive());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userUseCase.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}