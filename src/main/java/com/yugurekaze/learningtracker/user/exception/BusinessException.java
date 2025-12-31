package com.yugurekaze.learningtracker.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BusinessException extends RuntimeException {

    private final String messageKey;
    private final HttpStatus status;
    private final Object[] args;

    protected BusinessException(String messageKey, HttpStatus status, Object... args) {
        this.messageKey = messageKey;
        this.status = status;
        this.args = args;
    }
}