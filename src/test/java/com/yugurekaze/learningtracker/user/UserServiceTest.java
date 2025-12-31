package com.yugurekaze.learningtracker.user;

import com.yugurekaze.learningtracker.user.exception.exceptionImpls.UserNotFoundException;
import com.yugurekaze.learningtracker.user.exception.exceptionImpls.WrongEmailException;
import com.yugurekaze.learningtracker.user.mapper.UserMapper;
import com.yugurekaze.learningtracker.user.model.User;
import com.yugurekaze.learningtracker.user.model.dto.UserCreationRequest;
import com.yugurekaze.learningtracker.user.model.dto.UserResponse;
import com.yugurekaze.learningtracker.user.repository.UserRepository;
import com.yugurekaze.learningtracker.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnUserResponseWhenUserCreationSuccessful() {
        UserCreationRequest request = new UserCreationRequest("test@example.com", "password123");

        User userEntity = new User();
        userEntity.setEmail("test@example.com");
        userEntity.setPassword("password123");

        UserResponse expectedResponse = new UserResponse("test@example.com", LocalDateTime.now());

        // Мокаем mapper
        when(userMapper.mapToUserCreation(request)).thenReturn(userEntity);
        when(userMapper.mapToUserResponse(userEntity)).thenReturn(expectedResponse);
        when(userRepository.save(any(User.class))).thenReturn(userEntity);

        UserResponse actualResponse = userService.createUser(request);

        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void shouldReturnUserResponseWhenFindUserByIdSuccessful() {
        LocalDateTime fixedTime = LocalDateTime.of(2025, 1, 1, 12, 0, 0);
        User userEntity = new User();
        userEntity.setId(1L);
        userEntity.setEmail("test@example.com");
        userEntity.setCreatedAt(fixedTime);
        UserResponse expectedResponse = new UserResponse("test@example.com", fixedTime);

        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userMapper.mapToUserResponse(userEntity)).thenReturn(expectedResponse);

        UserResponse actualResponse = userService.getUserById(1L);
        assertThat(actualResponse).isEqualTo(expectedResponse);

    }

    @Test
    void shouldNotFindUserWhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void shouldReturnUserResponseWhenFindUserByEmailSuccessful() {
        LocalDateTime fixedTime = LocalDateTime.of(2025, 1, 1, 12, 0, 0);
        User userEntity = new User();
        userEntity.setId(1L);
        userEntity.setEmail("test@example.com");
        userEntity.setCreatedAt(fixedTime);
        UserResponse expectedResponse = new UserResponse("test@example.com", fixedTime);

        when(userRepository.findByEmail(userEntity.getEmail())).thenReturn(Optional.of(userEntity));
        when(userMapper.mapToUserResponse(userEntity)).thenReturn(expectedResponse);

        UserResponse actualResponse = userService.getUserByEmail(userEntity.getEmail());
        assertThat(actualResponse).isEqualTo(expectedResponse);
    }

    @Test
    void shouldNotFoundUserByEmailWhenEmailNullOrEmpty() {
        assertThrows(IllegalEmailException.class, () -> userService.getUserByEmail(null));
        assertThrows(IllegalEmailException.class, () -> userService.getUserByEmail(""));
    }

    @Test
    void shouldReturnUserResponseWhenChangeUserEmailSuccessful() {
        LocalDateTime fixedTime = LocalDateTime.of(2025, 1, 1, 12, 0, 0);
        User userEntity = new User();
        userEntity.setId(1L);
        userEntity.setEmail("test@example.com");
        userEntity.setCreatedAt(fixedTime);
        UserResponse expectedResponse = new UserResponse("new@example.com", fixedTime);

        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        when(userMapper.mapToUserResponse(userEntity)).thenReturn(expectedResponse);

        UserResponse actualResponse = userService.changeUserEmail(1L, "new@example.com");
        assertThat(actualResponse).isEqualTo(expectedResponse);
        assertThat(userEntity.getEmail()).isEqualTo("new@example.com");
    }

    @Test
    void shouldNotChangeUserEmailWhenEmailAlreadyExists() {
        User existingUser = new User();
        existingUser.setId(2L);
        existingUser.setEmail("existing@mail.com");

        when(userRepository.findByEmail("existing@mail.com"))
                .thenReturn(Optional.of(existingUser));

        assertThrows(WrongEmailException.class, () ->
                userService.changeUserEmail(1L, "existing@mail.com"));
    }

    @Test
    void shouldNotChangeUserEmailWhenEmailNullOrEmpty() {
        assertThrows(WrongEmailException.class, () -> userService.changeUserEmail(1L, null));
        assertThrows(WrongEmailException.class, () -> userService.changeUserEmail(1L, ""));
    }

    @Test
    void shouldNotChangeUserEmailWhenEmailsAreTheSame() {
        User userEntity = new User();
        userEntity.setId(1L);
        userEntity.setEmail("test@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));
        assertThrows(WrongEmailException.class, () -> userService.changeUserEmail(1L, "test@example.com"));
    }



}
