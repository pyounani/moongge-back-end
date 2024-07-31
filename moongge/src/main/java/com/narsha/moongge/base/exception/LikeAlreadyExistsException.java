package com.narsha.moongge.base.exception;

import com.narsha.moongge.base.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeAlreadyExistsException extends RuntimeException {

    private final ErrorCode errorCode;
}