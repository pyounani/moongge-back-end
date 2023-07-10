package com.example.narshaback.base.exception;

import com.example.narshaback.base.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;
}
