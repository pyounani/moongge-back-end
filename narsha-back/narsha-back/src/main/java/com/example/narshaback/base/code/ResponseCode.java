package com.example.narshaback.base.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    SUCCESS_REGISTER(HttpStatus.OK, "회원가입을 성공했습니다."),
    SUCCESS_LOGIN(HttpStatus.OK, "로그인을 성공했습니다."),

    SUCCESS_UNIQUE_ID(HttpStatus.OK, "중복된 아이디가 없습니다."),

    SUCCESS_JOIN_GROUP(HttpStatus.OK, "그룹에 가입되었습니다."),

    SUCCESS_CREATE_COMMENT(HttpStatus.OK, "댓글 작성을 성공했습니다."),
    SUCCESS_GET_COMMENT_LIST(HttpStatus.OK, "댓글 목록을 성공적으로 가져왔습니다."),
    SUCCESS_CREATE_LIKE(HttpStatus.OK, "좋아요 생성을 성공했습니다."),
    SUCCESS_GET_LIKE_LIST(HttpStatus.OK, "좋아요 목록을 성공적으로 가져왔습니다."),

    ;

    private final HttpStatus status;
    private final String message;
}
