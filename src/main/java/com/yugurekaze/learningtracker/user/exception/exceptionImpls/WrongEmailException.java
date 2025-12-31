package com.yugurekaze.learningtracker.user.exception.exceptionImpls;

import com.yugurekaze.learningtracker.user.exception.BusinessException;
import com.yugurekaze.learningtracker.user.exception.enums.WrongEmailReason;
import org.springframework.http.HttpStatus;

public class WrongEmailException extends BusinessException {
    public WrongEmailException(WrongEmailReason reason) {
        super(
                reason.getMessageKey(),
                HttpStatus.BAD_REQUEST
        );
    }
}
