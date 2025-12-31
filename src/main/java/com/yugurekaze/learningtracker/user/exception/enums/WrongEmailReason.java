package com.yugurekaze.learningtracker.user.exception.enums;

import lombok.Getter;

@Getter
public enum WrongEmailReason {
    EMAIL_ALREADY_EXISTS("error.email.already_exists"),
    EMAIL_EMPTY_OR_NULL("error.email.empty_or_null"),
    EMAIL_THE_SAME("error.email.same_as_old");

    private final String messageKey;

    WrongEmailReason(String messageKey) {
        this.messageKey = messageKey;

    }
}
