package com.yugurekaze.learningtracker.user.mapper;

import com.yugurekaze.learningtracker.user.model.User;
import com.yugurekaze.learningtracker.user.model.dto.UserCreationRequest;
import com.yugurekaze.learningtracker.user.model.dto.UserResponse;
import org.springframework.stereotype.Component;

//TODO replace with MapStruct
@Component
public class UserMapper {

    public UserResponse mapToUserResponse(User user) {
        return new UserResponse(
                user.getEmail(),
                user.getCreatedAt()
        );
    }


    public User mapToUserCreation(UserCreationRequest userCreationRequest) {
        return new User(
                null,
                userCreationRequest.email(),
                userCreationRequest.password(),
                null,
                null,
                null
        );
    }
}
