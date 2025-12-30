package com.yugurekaze.learningtracker.user;

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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
    void shouldReturnUserResponseWhenUserCreationSecessful() {
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
    void shouldReturnUserResponseWhenFindUserByIdSecessful() {
        User userEntity = new User();
        userEntity.setId(1L);
        userEntity.setEmail("test@example.com");
        userEntity.setCreatedAt(LocalDateTime.now());

        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        UserResponse expectedResponse = new UserResponse("test@example.com", LocalDateTime.now());

        UserResponse actualResponse = userService.getUserById(1L);

        assertThat(actualResponse).isEqualTo(expectedResponse);

    }
}
