package com.example.narshaback.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginIdNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;
}
