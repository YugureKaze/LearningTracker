package com.yugurekaze.learningtracker.user.service;

import com.yugurekaze.learningtracker.user.exception.enums.WrongEmailReason;
import com.yugurekaze.learningtracker.user.exception.enums.WrongPasswordReason;
import com.yugurekaze.learningtracker.user.exception.exceptionImpls.UserNotFoundException;
import com.yugurekaze.learningtracker.user.exception.exceptionImpls.WrongEmailException;
import com.yugurekaze.learningtracker.user.exception.exceptionImpls.WrongPasswordException;
import com.yugurekaze.learningtracker.user.mapper.UserMapper;
import com.yugurekaze.learningtracker.user.model.User;
import com.yugurekaze.learningtracker.user.model.dto.UserCreationRequest;
import com.yugurekaze.learningtracker.user.model.dto.UserResponse;
import com.yugurekaze.learningtracker.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.mapToUserResponse(user);
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse getUserByEmail(String email) {
        if (email == null || email.isEmpty()) {
            log.error("Method getUserByEmail called with empty or null email");
            throw new WrongEmailException(WrongEmailReason.EMAIL_EMPTY_OR_NULL);
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        return userMapper.mapToUserResponse(user);
    }

    @Transactional
    @Override
    public UserResponse createUser(UserCreationRequest userCreationRequest) {
        User user = userMapper.mapToUserCreation(userCreationRequest);
        userRepository.save(user);
        return userMapper.mapToUserResponse(user);
    }

    @Transactional
    @Override
    public UserResponse changeUserEmail(Long id, String newEmail) {
        if(newEmail == null || newEmail.isEmpty()) {
            log.error("Method changeUserEmail called with empty or null newEmail");
            throw new WrongEmailException(WrongEmailReason.EMAIL_EMPTY_OR_NULL);
        }
        User existing = userRepository.findByEmail(newEmail)
                .orElse(null);
        if (existing != null) {
            throw new WrongEmailException(WrongEmailReason.EMAIL_ALREADY_EXISTS);
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        if (user.getEmail().equals(newEmail)) {
            log.error("User with id {} tried to change email to the same email", id);
            throw new WrongEmailException(WrongEmailReason.EMAIL_THE_SAME);
        }
        user.setEmail(newEmail);
        log.info("User with id {} changed email to {} at {}", id, newEmail, LocalDateTime.now());
        return userMapper.mapToUserResponse(user);
    }
    //TODO добавить событие через kafka который пишет в ClickHouse для аналитики
    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
        log.info("User with id {} was deleted at {}", id, LocalDateTime.now());
    }
    //TODO изменить смену пароля, сейчас не безопасное решение, чисто как каркас
    @Transactional
    @Override
    public void changeUserPassword(Long id, String newPassword, String oldPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        if (!user.getPassword().equals(oldPassword)) {
            log.error("User with id {} tried to change password with wrong old password", id);
            throw new WrongPasswordException(WrongPasswordReason.OLD_PASSWORD_MISMATCH);
        }
        if (newPassword.equals(oldPassword)) {
            log.error("User with id {} tried to change password to the same password", id);
            throw new WrongPasswordException(WrongPasswordReason.SAME_AS_OLD);
        }
        userRepository.changeUserPassword(id, newPassword);
        log.info("User with id {} changed password at {}", id, LocalDateTime.now());
    }

}
