package com.narsha.moongge.entity;

import com.narsha.moongge.base.code.ErrorCode;
import com.narsha.moongge.base.exception.InvalidUserTypeException;

public enum UserType {
    STUDENT,
    TEACHER;

    public static UserType fromString(String userType) {
        try {
            return UserType.valueOf(userType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidUserTypeException(ErrorCode.INVALID_USER_TYPE);
        }
    }
}
