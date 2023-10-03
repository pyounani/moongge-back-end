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
    SUCCESS_GET_UNLIKED_POSTS(HttpStatus.OK, "사용자 좋아요를 누르지 않은 게시물 목록을 성공적으로 가져왔습니다."),

    SUCCESS_UPDATE_PROFILE(HttpStatus.OK, "프로필 수정에 성공했습니다."),
    SUCCESS_GET_PROFILE(HttpStatus.OK, "프로필을 성공적으로 가져왔습니다."),
    SUCCESS_GET_BADGE_LIST(HttpStatus.OK, "뱃지 목록을 성공적으로 가져왔습니다."),
    SUCCESS_UPDATE_BADGE_LIST(HttpStatus.OK, "뱃지 목록을 성공적으로 업데이트했습니다."),


    SUCCESS_CREATE_COMMENT(HttpStatus.OK, "댓글 작성을 성공했습니다."),
    SUCCESS_GET_COMMENT_LIST(HttpStatus.OK, "댓글 목록을 성공적으로 가져왔습니다."),
    SUCCESS_GET_RECENT_COMMENT(HttpStatus.OK, "최신 댓글을 성공적으로 가져왔습니다."),
    SUCCESS_GET_COMMENT_COUNT(HttpStatus.OK, "댓글 갯수를 성공적으로 가져왔습니다."),
    SUCCESS_CREATE_LIKE(HttpStatus.OK, "좋아요 생성을 성공했습니다."),
    SUCCESS_GET_LIKE_LIST(HttpStatus.OK, "좋아요 목록을 성공적으로 가져왔습니다."),
    SUCCESS_CHECK_LIKE_POST(HttpStatus.OK, "좋아요 여부를 성공적으로 가져왔습니다."),
    SUCCESS_DELETE_LIKE(HttpStatus.OK, "좋아요를 성공적으로 취소했습니다."),
    SUCCESS_COUNT_LIKE(HttpStatus.OK, "좋아요 개수를 성공적으로 가져왔습니다."),
    SUCCESS_RECEIVE_TEN_LIKE(HttpStatus.OK, "좋아요 10개 받기 달성 여부를 성공적으로 가져왔습니다."),
    SUCCESS_GIVE_TEN_LIKE(HttpStatus.OK, "좋아요 10개 주기 달성 여부를 성공적으로 가져왔습니다."),

    SUCCESS_CREATE_GROUP(HttpStatus.OK, "그룹 생성 성공"),

    SUCCESS_GET_GROUP_CODE(HttpStatus.OK,"그룹 코드 가져오기 성공"),

    SUCCESS_DELETE_GROUP(HttpStatus.OK, "그룹 삭제 성공"),

    SUCCESS_UPLOAD_POST(HttpStatus.OK, "포스트 업로드 완료!"),

    SUCCESS_IMAGE_MASKING(HttpStatus.OK, "이미지 마스킹 성공"),
    SUCCESS_GET_POST_LIST(HttpStatus.OK,"사용자 게시글 목록 불러오기 성공"),
    SUCCESS_TEXT_FILTERING(HttpStatus.OK,"텍스트 필터링 성공"),

    SUCCESS_DETAIL_POST(HttpStatus.OK, "포스트 상세 불러오기 성공"),

    SUCCESS_UPDATE_TIME(HttpStatus.OK, "앱 사용시간 설정 성공"),

    SUCCESS_GET_TIME(HttpStatus.OK, "앱 사용시간 불러오기 성공"),
    SUCCESS_GET_ALARM_LIST(HttpStatus.OK, "알람 목록을 성공적으로 가져왔습니다."),
    SUCCESS_DELETE_ALARM(HttpStatus.OK, "알람을 성공적으로 삭제했습니다."),

    SUCCESS_SAVE_FCM_TOKEN(HttpStatus.OK, "Fcm 토큰을 성공적으로 가져왔습니다."),

    ;

    private final HttpStatus status;
    private final String message;
}
