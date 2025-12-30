package com.yugurekaze.learningtracker.user.exception;

public class WrongEmailException extends RuntimeException {
    public WrongEmailException(String message) {
        super(message);
    }
}
