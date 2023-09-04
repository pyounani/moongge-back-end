package com.example.narshaback.listener;


import com.example.narshaback.base.code.ErrorCode;
import com.example.narshaback.base.dto.alarm.FcmAlarmRequestDto;
import com.example.narshaback.base.exception.PostNotFoundException;
import com.example.narshaback.entity.AlarmEntity;
import com.example.narshaback.entity.PostEntity;
import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.event.AlarmCreatedEvent;
import com.example.narshaback.repository.PostRepository;
import com.example.narshaback.repository.UserRepository;
import com.example.narshaback.service.AlarmService;
import com.example.narshaback.service.FcmAlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AlarmEventListener {

    private final AlarmService alarmService;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final FcmAlarmService fcmAlarmService;

    @EventListener
    @Transactional
    public void handleAlarmCreatedEvent(AlarmCreatedEvent event) {
        AlarmEntity alarm = event.getAlarm();

        String user;
        Optional<PostEntity> post = postRepository.findByPostId(alarm.getPostId().getPostId());
        if(post.isPresent()) {
            user = post.get().getUser().getUserId();

            String title;
            String body;

            //alarm.getUserId();

            if ("LIKE".equals(alarm.getActionType())) {
                title = "좋아요 알림";
                body = "좋아요가 추가되었습니다.";
            } else if ("COMMENT".equals(alarm.getActionType())) {
                title = "댓글 알림";
                body = "새로운 댓글이 달렸습니다.";
            } else {
                title = "기타 알림";
                body = "새로운 알림이 생성되었습니다.";
            }

            FcmAlarmRequestDto fcmAlarmRequestDto = new FcmAlarmRequestDto();
            fcmAlarmRequestDto.setUserId(user);
            fcmAlarmRequestDto.setTitle(title);
            fcmAlarmRequestDto.setBody(body);

            // FCM 푸시 알림 보내기
            String messageId = fcmAlarmService.sendAlarmByToken(fcmAlarmRequestDto);
            System.out.println(messageId);

        } else {
            throw new PostNotFoundException(ErrorCode.POSTS_NOT_FOUND);
        }

    }
    private String getUserFcmToken(String userId) {
        Optional<UserEntity> user = userRepository.findByUserId(userId);
        return user.map(UserEntity::getFcmToken).orElse(null);
    }

}
