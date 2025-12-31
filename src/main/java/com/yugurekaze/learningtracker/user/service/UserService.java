package com.yugurekaze.learningtracker.user.service;

import com.yugurekaze.learningtracker.user.model.dto.UserCreationRequest;
import com.yugurekaze.learningtracker.user.model.dto.UserResponse;

public interface UserService {
    UserResponse getUserById(Long id);
    UserResponse getUserByEmail(String email);
    UserResponse createUser(UserCreationRequest userCreationRequest);
    UserResponse changeUserEmail(Long id, String newEmail);
    void deleteUser(Long id);
    void changeUserPassword(Long id, String newPassword, String oldPassword);
}
