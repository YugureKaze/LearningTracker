package com.yugurekaze.learningtracker.user.controller;

import com.yugurekaze.learningtracker.user.model.dto.ChangeEmailRequest;
import com.yugurekaze.learningtracker.user.model.dto.ChangePasswordRequest;
import com.yugurekaze.learningtracker.user.model.dto.UserCreationRequest;
import com.yugurekaze.learningtracker.user.model.dto.UserResponse;
import com.yugurekaze.learningtracker.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Validated
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse userResponse = userService.getUserById(id);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping
    public ResponseEntity<UserResponse> getUserByEmail(@RequestParam @NotBlank String email) {
        UserResponse userResponse = userService.getUserByEmail(email);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping()
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserCreationRequest userCreationRequest) {
        UserResponse userResponse = userService.createUser(userCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PatchMapping("/{id}/email")
    public ResponseEntity<UserResponse> changeUserEmail(@PathVariable Long id, @RequestBody @Valid ChangeEmailRequest changeEmailRequest) {
        UserResponse userResponse = userService.changeUserEmail(id, changeEmailRequest.newEmail());
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> changeUserPassword(@PathVariable Long id,
                                                   @RequestBody @Valid ChangePasswordRequest changePasswordRequest) {
        userService.changeUserPassword(id, changePasswordRequest.newPassword(), changePasswordRequest.oldPassword());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
