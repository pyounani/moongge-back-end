package com.example.narshaback.listener;
import com.example.narshaback.event.CommentCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.example.narshaback.entity.CommentEntity;
import com.example.narshaback.entity.AlarmEntity;
import com.example.narshaback.repository.AlarmRepository;

import java.time.LocalDateTime;

@Component
public class CommentEventListener {

    private final AlarmRepository alarmRepository;

    public CommentEventListener(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    @EventListener
    @Transactional
    public void handleCommentCreatedEvent(CommentCreatedEvent event) {
        CommentEntity comment = event.getComment();

        // 알림 엔티티 생성
        AlarmEntity alarm = new AlarmEntity();
        alarm.setActionType("COMMENT"); // 액션 유형 설정
        alarm.setUserId(comment.getUserId()); // 대상 이벤트를 일으킨 ID 설정
        alarm.setCreatedAt(LocalDateTime.now()); // 생성 시각 설정
        alarm.setCommentId(comment);
        alarm.setPostId(comment.getPostId());

        // 알림 엔티티 저장
        alarmRepository.save(alarm);
    }
}

