package com.narsha.moongge.base.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     * 400 BAD_REQUEST: 잘못된 요청
     */
    INVALID_GROUP_CODE(HttpStatus.BAD_REQUEST, "잘못된 그룹 코드입니다."),
    INVALID_USER_TYPE(HttpStatus.BAD_REQUEST, "잘못된 유저 타입입니다."),
    EMPTY_COMMENT_CONTENT(HttpStatus.BAD_REQUEST, "댓글 내용이 비어있습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    DELETE_FAILED_ENTITY_RELATED_GROUPCODE(HttpStatus.BAD_REQUEST, "해당 그룹코드로 모든 엔티티 삭제를 실패했습니다."),

    /**
     * 401 UNAUTHORIZED
     */
    PASSWORD_NOT_MATCH(HttpStatus.UNAUTHORIZED, "비밀번호가 올바르지 않습니다."),

    /**
     * 403 FORBIDDEN
     */
    STUDENT_NOT_ALLOWED_GROUP(HttpStatus.FORBIDDEN, "학생은 그룹을 생성할 수 없습니다."),

    STUDENT_NOT_ALLOWED_NOTICE(HttpStatus.FORBIDDEN, "학생은 공지사항은 작성할 수 없습니다."),
    GROUP_MISMATCH(HttpStatus.FORBIDDEN, "그룹 코드가 일치하지 않습니다."),

    /**
     * 404 NOT_FOUND: 리소스를 찾을 수 없음
     */
    USERID_NOT_FOUND(HttpStatus.NOT_FOUND, "아이디가 존재하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "아이디에 해당하는 유저를 찾을 수 없습니다."),
    GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "그룹이 생성되지 않았습니다."),
    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "공지사항을 찾을 수 없습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글 정보를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글 정보를 찾을 수 없습니다."),

    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "좋아요를 찾을 수 없습니다."),

    /*
     * 405 METHOD_NOT_ALLOWED: 허용되지 않은 Request Method 호출
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다."),

    /**
     * 409 CONFLICT
     */
    DUPLICATE_ID_REQUEST(HttpStatus.CONFLICT, "중복된 아이디가 있습니다."),
    ACHIEVEMENT_ALREADY_COMPLETED(HttpStatus.CONFLICT, "이미 달성된 업적입니다."),

    LIKE_ALREADY_EXISTS(HttpStatus.CONFLICT, "좋아요가 이미 생성되어있습니다."),

    /*
     * 500 INTERNAL_SERVER_ERROR: 내부 서버 오류
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류입니다."),

    ;

    private final HttpStatus status;
    private final String message;
}
