package com.yugurekaze.learningtracker.user.exception.exceptionImpls;

import com.yugurekaze.learningtracker.user.exception.BusinessException;
import com.yugurekaze.learningtracker.user.exception.enums.WrongPasswordReason;
import org.springframework.http.HttpStatus;

public class WrongPasswordException extends BusinessException {

    public WrongPasswordException(WrongPasswordReason reason) {
        super(
                reason.getMessageKey(),
                HttpStatus.BAD_REQUEST
        );
    }
}
