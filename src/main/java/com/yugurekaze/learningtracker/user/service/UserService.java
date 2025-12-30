package com.yugurekaze.learningtracker.user.service;

import com.yugurekaze.learningtracker.user.exception.IllegalEmailException;
import com.yugurekaze.learningtracker.user.exception.UserNotFoundException;
import com.yugurekaze.learningtracker.user.exception.WrongPasswordException;
import com.yugurekaze.learningtracker.user.mapper.UserMapper;
import com.yugurekaze.learningtracker.user.model.User;
import com.yugurekaze.learningtracker.user.model.dto.UserCreationRequest;
import com.yugurekaze.learningtracker.user.model.dto.UserResponse;
import com.yugurekaze.learningtracker.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return userMapper.mapToUserResponse(user);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserByEmail(String email) {
        if (email == null || email.isEmpty()) {
            log.error("Method getUserByEmail called with empty or null email");
            throw new IllegalEmailException("Email is empty or null");
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return userMapper.mapToUserResponse(user);
    }

    @Transactional
    public UserResponse createUser(@Valid UserCreationRequest userCreationRequest) {
        User user = userMapper.mapToUserCreation(userCreationRequest);
        userRepository.save(user);
        return userMapper.mapToUserResponse(user);
    }

    @Transactional
    public UserResponse changeUserEmail(Long id, String newEmail) {
        userRepository.changeUserEmail(id, newEmail);
        log.info("User with id {} changed email to {} at {}", id, newEmail, LocalDateTime.now());
        return getUserById(id);
    }
    //TODO добавить событие через kafka который пишет в ClickHouse для аналитики
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        log.info("User with id {} was deleted at {}", id, LocalDateTime.now());
    }
    //TODO изменить смену пароля, сейчас не безопасное решение, чисто как каркас
    @Transactional
    public void changeUserPassword(Long id, String newPassword, String oldPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        if (!user.getPassword().equals(oldPassword)) {
            log.error("User with id {} tried to change password with wrong old password", id);
            throw new WrongPasswordException("Wrong old password");
        }
        if (newPassword.equals(oldPassword)) {
            log.error("User with id {} tried to change password to the same password", id);
            throw new WrongPasswordException("New password is the same as old password");
        }
        userRepository.changeUserPassword(id, newPassword);
        log.info("User with id {} changed password at {}", id, LocalDateTime.now());
    }

}
