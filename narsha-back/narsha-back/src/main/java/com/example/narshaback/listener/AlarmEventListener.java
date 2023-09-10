package com.example.narshaback.listener;


import com.example.narshaback.base.code.ErrorCode;
import com.example.narshaback.base.dto.alarm.FcmAlarmRequestDto;
import com.example.narshaback.base.exception.GroupNotFoundException;
import com.example.narshaback.base.exception.PostNotFoundException;
import com.example.narshaback.base.projection.user.GetUser;
import com.example.narshaback.entity.*;
import com.example.narshaback.event.AlarmCreatedEvent;
import com.example.narshaback.repository.NoticeRepository;
import com.example.narshaback.repository.PostRepository;
import com.example.narshaback.repository.UserRepository;
import com.example.narshaback.service.AlarmService;
import com.example.narshaback.service.FcmAlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AlarmEventListener {

    private final AlarmService alarmService;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final FcmAlarmService fcmAlarmService;

    private final NoticeRepository noticeRepository;

    @EventListener
    @Transactional
    public void handleAlarmCreatedEvent(AlarmCreatedEvent event) {
        AlarmEntity alarm = event.getAlarm();

        String title = "";
        String body = "";
        String user;
        GroupEntity groupCode;

        // 공지 알림
        if("NOTICE".equals(alarm.getActionType())) {
            // 그룹코드에 있는 사용자들 토큰 가져오기
            // 각각 알림 다 보내기

            groupCode = noticeRepository.findByNoticeId(alarm.getNoticeId().getNoticeId()).get().getGroupCode();
            if(groupCode == null) {
                throw new GroupNotFoundException(ErrorCode.GROUP_NOT_FOUND);
            }

            List<GetUser> userList = userRepository.findByGroupCode(groupCode);

            title = "공지 알림";
            body = "공지가 추가되었습니다.";

            for(GetUser notice_user : userList) {

                FcmAlarmRequestDto fcmAlarmRequestDto = new FcmAlarmRequestDto();
                fcmAlarmRequestDto.setUserId(notice_user.getUserId());
                fcmAlarmRequestDto.setTitle(title);
                fcmAlarmRequestDto.setBody(body);

                // FCM 푸시 알림 보내기
                String messageId = fcmAlarmService.sendAlarmByToken(fcmAlarmRequestDto);
                System.out.println(messageId);
            }

        } else if("LIKE".equals(alarm.getActionType()) || "COMMENT".equals(alarm.getActionType())){ // 좋아요 알림, 댓글 알림

            Optional<PostEntity> post = postRepository.findByPostId(alarm.getPostId().getPostId());
            if(!post.isPresent()) {
                throw new PostNotFoundException(ErrorCode.POSTS_NOT_FOUND);
            }

            user = post.get().getUser().getUserId();

            if ("LIKE".equals(alarm.getActionType())) {
                title = "좋아요 알림";
                body = "좋아요가 추가되었습니다.";
            } else if ("COMMENT".equals(alarm.getActionType())) {
                title = "댓글 알림";
                body = "새로운 댓글이 달렸습니다.";
            }

            FcmAlarmRequestDto fcmAlarmRequestDto = new FcmAlarmRequestDto();
            fcmAlarmRequestDto.setUserId(user);
            fcmAlarmRequestDto.setTitle(title);
            fcmAlarmRequestDto.setBody(body);

            // FCM 푸시 알림 보내기
            String messageId = fcmAlarmService.sendAlarmByToken(fcmAlarmRequestDto);
            System.out.println(messageId);
        }


    }

    private String getUserFcmToken(String userId) {
        Optional<UserEntity> user = userRepository.findByUserId(userId);
        return user.map(UserEntity::getFcmToken).orElse(null);
    }

}
