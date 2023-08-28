package com.example.narshaback.listener;

import com.example.narshaback.entity.AlarmEntity;
import com.example.narshaback.entity.NoticeEntity;
import com.example.narshaback.event.NoticeCreatedEvent;
import com.example.narshaback.repository.AlarmRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class NoticeEventListener  {

    private final AlarmRepository alarmRepository;

    public NoticeEventListener(AlarmRepository alarmRepository) {this.alarmRepository = alarmRepository;}

    @EventListener
    @Transactional
    public void handleNoticeCreatedEvent(NoticeCreatedEvent event) {
        NoticeEntity notice = event.getNotice();

        // 알림 엔티티 생성
        AlarmEntity alarm = new AlarmEntity();
        alarm.setActionType("NOTICE"); // 액션 유형 설정
        alarm.setUserId(notice.getWriter()); // 대상 ID 설정
        alarm.setCreatedAt(LocalDateTime.now()); // 생성 시각 설정
        alarm.setNoticeId(notice);
        alarm.setGroupCode(notice.getGroupCode());

        // 알림 엔티티 저장
        alarmRepository.save(alarm);
    }
}
