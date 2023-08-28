package com.example.narshaback.listener;

import com.example.narshaback.entity.AlarmEntity;
import com.example.narshaback.entity.LikeEntity;
import com.example.narshaback.event.LikeCreatedEvent;
import com.example.narshaback.repository.AlarmRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class LikeEventListener {

    private final AlarmRepository alarmRepository;

    public LikeEventListener(AlarmRepository alarmRepository) {
        this.alarmRepository = alarmRepository;
    }

    @EventListener
    @Transactional
    public void handleLikeCreatedEvent(LikeCreatedEvent event) {
        LikeEntity like = event.getLike();

        // 알림 엔티티 생성
        AlarmEntity alarm = new AlarmEntity();
        alarm.setActionType("LIKE"); // 액션 유형 설정
        alarm.setUserId(like.getUserId()); // 대상 ID 설정
        alarm.setCreatedAt(LocalDateTime.now()); // 생성 시각 설정
        alarm.setLikeId(like);
        alarm.setPostId(like.getPostId());


        // 알림 엔티티 저장
        alarmRepository.save(alarm);
    }

}
