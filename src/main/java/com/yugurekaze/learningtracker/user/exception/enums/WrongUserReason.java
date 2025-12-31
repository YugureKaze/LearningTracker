package com.yugurekaze.learningtracker.user.exception.enums;

import lombok.Getter;

@Getter
public enum WrongUserReason {
    USER_NOT_FOUND("error.user.not_found"),
    USER_NOT_FOUND_BY_EMAIL("error.user.not_found_by_email");

    private final String messageKey;

    WrongUserReason(String messageKey) {
        this.messageKey = messageKey;
    }
}
