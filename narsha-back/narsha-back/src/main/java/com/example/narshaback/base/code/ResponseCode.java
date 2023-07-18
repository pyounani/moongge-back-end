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
//    SUCCESS_FRIENDS_LIST(HttpStatus.OK, "그룹 사용자 목록 가져오기 성공"),

    SUCCESS_JOIN_GROUP(HttpStatus.OK, "그룹에 가입되었습니다."),

    SUCCESS_GET_USER_LIST(HttpStatus.OK, "그룹의 유저 목록을 성공적으로 가져왔습니다."),

    SUCCESS_CREATE_NOTICE(HttpStatus.OK, "공지 작성에 성공했습니다."),
    SUCCESS_GET_NOTICE_LIST(HttpStatus.OK, "공지 목록을 성공적으로 가져왔습니다."),
    SUCCESS_GET_NOTICE_DETAIL(HttpStatus.OK, "공지 상세를 성공적으로 가져왔습니다."),
    SUCCESS_GET_NOTICE_RECENT_ONE(HttpStatus.OK, "가장 최근 공지를 성공적으로 가져왔습니다."),

    SUCCESS_UPDATE_PROFILE(HttpStatus.OK, "프로필 수정에 성공했습니다."),
    SUCCESS_GET_PROFILE(HttpStatus.OK, "프로필을 성공적으로 가져왔습니다."),
    SUCCESS_GET_BADGE_LIST(HttpStatus.OK, "뱃지 목록을 성공적으로 가져왔습니다."),
    SUCCESS_UPDATE_BADGE_LIST(HttpStatus.OK, "뱃지 목록을 성공적으로 업데이트했습니다."),


    SUCCESS_CREATE_COMMENT(HttpStatus.OK, "댓글 작성을 성공했습니다."),
    SUCCESS_GET_COMMENT_LIST(HttpStatus.OK, "댓글 목록을 성공적으로 가져왔습니다."),
    SUCCESS_CREATE_LIKE(HttpStatus.OK, "좋아요 생성을 성공했습니다."),
    SUCCESS_GET_LIKE_LIST(HttpStatus.OK, "좋아요 목록을 성공적으로 가져왔습니다."),

    SUCCESS_CREATE_GROUP(HttpStatus.OK, "그룹 생성 성공"),

    SUCCESS_GET_GROUP_CODE(HttpStatus.OK,"그룹 코드 가져오기 성공"),

    SUCCESS_UPLOAD_POST(HttpStatus.OK, "포스트 업로드 완료!"),

    SUCCESS_GET_POST_LIST(HttpStatus.OK,"사용자 게시글 목록 불러오기 성공"),

    SUCCESS_DETAIL_POST(HttpStatus.OK, "포스트 상세 불러오기 성공"),


    ;

    private final HttpStatus status;
    private final String message;
}
