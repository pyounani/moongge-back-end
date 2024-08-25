package com.narsha.moongge.base.exception;

import com.narsha.moongge.base.code.ErrorCode;
import com.narsha.moongge.base.dto.response.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 컨트롤러 전역에서 발생하는 예외 throw
@Slf4j // 자동 로그 객체 생성
public class GlobalExceptionHandler {

    /**
     * 입력값 검증
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append(fieldError.getDefaultMessage());
        }

        log.error("handleMethodArgumentNotValidException : {}", builder.toString());
        return ResponseEntity
                .status(ErrorCode.BAD_REQUEST.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.BAD_REQUEST, builder.toString()));
    }

    /**
     * User
     */

    // 아이디 중복 확인
    @ExceptionHandler(RegisterException.class)
    protected ResponseEntity<ErrorResponseDTO> handleRegisterException(final RegisterException e) {
        log.error("handleRegisterException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.DUPLICATE_ID_REQUEST.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.DUPLICATE_ID_REQUEST));
    }

    // 유저가 존재하지 않을 때
    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<ErrorResponseDTO> handleUserNotFoundException(final UserNotFoundException e) {
        log.error("handleUserNotFoundException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.USER_NOT_FOUND.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.USER_NOT_FOUND));
    }

    // 비밀번호가 일치하지 않을 때
    @ExceptionHandler(LoginPasswordNotMatchException.class)
    protected ResponseEntity<ErrorResponseDTO> handleLoginPasswordNotMatchException(final LoginPasswordNotMatchException e) {
        log.error("LoginPasswordNotMatchException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.PASSWORD_NOT_MATCH.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.PASSWORD_NOT_MATCH));
    }

    // 이미 달성된 업적일 떄
    @ExceptionHandler(AchievementAlreadyCompletedException.class)
    protected ResponseEntity<ErrorResponseDTO> handleAchievementAlreadyCompletedException(final AchievementAlreadyCompletedException e) {
        log.error("handleAchievementAlreadyCompletedException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.ACHIEVEMENT_ALREADY_COMPLETED.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.ACHIEVEMENT_ALREADY_COMPLETED));
    }


    /**
     * Group
     */

    // 그룹: 그룹 코드에 해당하는 그룹이 존재하지 않을 때
    @ExceptionHandler(GroupNotFoundException.class)
    protected ResponseEntity<ErrorResponseDTO> handleGroupNotFoundException(final GroupNotFoundException e) {
        log.error("GroupNotFoundException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.GROUP_NOT_FOUND.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.GROUP_NOT_FOUND));
    }

    @ExceptionHandler(StudentGroupCreationException.class)
    protected ResponseEntity<ErrorResponseDTO> handleStudentGroupCreationException(final StudentGroupCreationException e) {
        log.error("StudentGroupCreationException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.STUDENT_NOT_ALLOWED_GROUP.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.STUDENT_NOT_ALLOWED_GROUP));
    }

    @ExceptionHandler(InvalidGroupCodeException.class)
    protected ResponseEntity<ErrorResponseDTO> handleInvalidGroupCodeException(final InvalidGroupCodeException e) {
        log.error("handleInvalidGroupCodeException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.INVALID_GROUP_CODE.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.INVALID_GROUP_CODE));
    }

    /**
     * Comment
     */


    // 댓글: 댓글이 비어있을 때

    @ExceptionHandler(EmptyCommentContentException.class)
    protected ResponseEntity<ErrorResponseDTO> handleEmptyCommentContentException(final EmptyCommentContentException e) {
        log.error("EmptyCommentContentException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.EMPTY_COMMENT_CONTENT.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.EMPTY_COMMENT_CONTENT));
    }

    /**
     * HTTP 405 Exception
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponseDTO> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException: {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.METHOD_NOT_ALLOWED.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.METHOD_NOT_ALLOWED));
    }

    /**
     * Post
     */
    @ExceptionHandler(PostNotFoundException.class)
    protected ResponseEntity<ErrorResponseDTO> handlePostNotFoundException(final PostNotFoundException e) {
        log.error("PostNotFoundException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.POST_NOT_FOUND.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.POST_NOT_FOUND));
    }

    @ExceptionHandler(NoticeNotFoundException.class)
    protected ResponseEntity<ErrorResponseDTO>  handleNoticeNotFoundException(final NoticeNotFoundException e) {
        log.error("NoticeNotFoundException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.NOTICE_NOT_FOUND.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.NOTICE_NOT_FOUND));

    }

    /**
     * NOTICE
     */
    @ExceptionHandler(StudentNoticeCreationException.class)
    protected ResponseEntity<ErrorResponseDTO>  handleStudentNoticeCreationException(final StudentNoticeCreationException e) {
        log.error("StudentNoticeCreationException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.STUDENT_NOT_ALLOWED_NOTICE.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.STUDENT_NOT_ALLOWED_NOTICE));

    }

    @ExceptionHandler(GroupMismatchException.class)
    protected ResponseEntity<ErrorResponseDTO>  handleGroupMismatchException(final GroupMismatchException e) {
        log.error("GroupMismatchException : {}", e.getMessage());
        return ResponseEntity
                .status(ErrorCode.GROUP_MISMATCH.getStatus().value())
                .body(new ErrorResponseDTO(ErrorCode.GROUP_MISMATCH));

    }

}
