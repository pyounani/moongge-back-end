package com.example.narshaback.event;

import com.example.narshaback.entity.NoticeEntity;
import org.springframework.context.ApplicationEvent;

public class NoticeCreatedEvent extends ApplicationEvent {

    private NoticeEntity notice;

    public NoticeCreatedEvent(Object source, NoticeEntity notice) {
        super(source);
        this.notice = notice;
    }

    public NoticeEntity getNotice() {
        return notice;
    }
}
