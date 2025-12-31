package com.yugurekaze.learningtracker.user.exception.enums;

import lombok.Getter;

@Getter
public enum WrongPasswordReason {
    OLD_PASSWORD_MISMATCH("error.password.old_mismatch"),
    SAME_AS_OLD("error.password.same_as_old");

    private final String messageKey;

    WrongPasswordReason(String messageKey) {
        this.messageKey = messageKey;
    }

}
