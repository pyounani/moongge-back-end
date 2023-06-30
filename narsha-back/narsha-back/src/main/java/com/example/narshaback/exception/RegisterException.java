package com.example.narshaback.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterException extends RuntimeException {
    private final ErrorCode errorCode;
}
