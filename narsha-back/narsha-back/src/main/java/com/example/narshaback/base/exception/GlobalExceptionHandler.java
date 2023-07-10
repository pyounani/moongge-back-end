package com.example.narshaback.base.exception;

import com.example.narshaback.base.code.ErrorCode;
import com.example.narshaback.base.dto.response.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 컨트롤러 전역에서 발생하는 예외 throw
@Slf4j // 자동 로그 객체 생성
public class GlobalExceptionHandler {
    /*
     * Developer Custom Exception
     */

    /* User */

    // 회원가입: 아이디 중복 확인
    @ExceptionHandler(RegisterException.class)
    protected ResponseEntity<ErrorResponseDTO> handleRegisterException(final RegisterException e) {
        log.error("handleRegisterException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.DUPLICATE_ID_REQUEST.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.DUPLICATE_ID_REQUEST));
    }

    // 로그인: 존재하는 아이디가 없을 때
    @ExceptionHandler(LoginIdNotFoundException.class)
    protected ResponseEntity<ErrorResponseDTO> handleLoginIdNotFoundException(final LoginIdNotFoundException e) {
        log.error("LoginIdNotFoundException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.USERID_NOT_FOUND.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.USERID_NOT_FOUND));
    }

    // 로그인: 비밀번호가 일치하지 않을 때
    @ExceptionHandler(LoginPasswordNotMatchException.class)
    protected ResponseEntity<ErrorResponseDTO> handleLoginPasswordNotMatchException(final LoginPasswordNotMatchException e) {
        log.error("LoginPasswordNotMatchException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.PASSWORD_NOT_MATCH.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.PASSWORD_NOT_MATCH));
    }

    /* UserGroup */

    // 그룹: 그룹 코드에 해당하는 그룹이 존재하지 않을 때
    @ExceptionHandler(GroupCodeNotFoundException.class)
    protected ResponseEntity<ErrorResponseDTO> handleGroupCodeNotFoundException(final GroupCodeNotFoundException e) {
        log.error("GroupCodeNotFoundException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.GROUPCODE_NOT_FOUND.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.GROUPCODE_NOT_FOUND));
    }

    // 그룹: 그룹 코드에 해당하는 그룹이 존재하지 않을 때
    @ExceptionHandler(GroupNotFoundException.class)
    protected ResponseEntity<ErrorResponseDTO> handleGroupNotFoundException(final GroupNotFoundException e) {
        log.error("GroupNotFoundException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.GROUP_NOT_FOUND.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.GROUP_NOT_FOUND));
    }

    // 포스트: 포스트 아이디에 해당하는 포스트가 존재하지 않을 때
    @ExceptionHandler(PostNotFoundException.class)
    protected ResponseEntity<ErrorResponseDTO> hanglePostNotFoundException(final PostNotFoundException e) {
        log.error("PostNotFoundException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.POSTS_NOT_FOUND.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.POSTS_NOT_FOUND));
    }

    // 사용자: 댓글 불러올 때 사용자가 존재하지 않을 때
    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(final UserNotFoundException e) {
        log.error("UserNotFoundException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.USERID_NOT_FOUND.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.USERID_NOT_FOUND));
    }

    // 댓글: 댓글이 비어있을 때
    @ExceptionHandler(EmptyCommentContentException.class)
    protected ResponseEntity<ErrorResponseDTO> handleEmptyCommentContentException(final EmptyCommentContentException e) {
        log.error("EmptyCommentContentException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.EMPTY_COMMENT_CONTENT.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.EMPTY_COMMENT_CONTENT));
    }

    /*
     * HTTP 405 Exception
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponseDTO> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException: {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.METHOD_NOT_ALLOWED.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.METHOD_NOT_ALLOWED));
    }

    /*
     * HTTP 500 Exception
     */
//    @ExceptionHandler(Exception.class)
//    protected ResponseEntity<ErrorResponseDTO> handleException(final Exception e) {
//        log.error("handleException: {}", e.getMessage());
//        return ResponseEntity
//                .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus().value())
//                .body(new ErrorResponseDTO(ErrorCode.INTERNAL_SERVER_ERROR));
//    }
}
