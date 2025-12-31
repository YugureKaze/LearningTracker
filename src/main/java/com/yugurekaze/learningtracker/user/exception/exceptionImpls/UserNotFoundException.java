package com.yugurekaze.learningtracker.user.exception.exceptionImpls;

import com.yugurekaze.learningtracker.user.exception.BusinessException;
import com.yugurekaze.learningtracker.user.exception.enums.WrongUserReason;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(Long id) {
        super(
                WrongUserReason.USER_NOT_FOUND.getMessageKey(),
                HttpStatus.NOT_FOUND,
                id

        );
    }
    public UserNotFoundException(String email) {
        super(
                WrongUserReason.USER_NOT_FOUND_BY_EMAIL.getMessageKey(),
                HttpStatus.NOT_FOUND,
                email

        );
    }
}
