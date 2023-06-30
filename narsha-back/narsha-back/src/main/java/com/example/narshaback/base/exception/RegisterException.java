package com.example.narshaback.base.exception;

import com.example.narshaback.base.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterException extends RuntimeException {
    private final ErrorCode errorCode;
}
