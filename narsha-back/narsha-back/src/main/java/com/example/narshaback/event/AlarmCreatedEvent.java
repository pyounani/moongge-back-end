package com.example.narshaback.event;

import com.example.narshaback.entity.AlarmEntity;
import org.springframework.context.ApplicationEvent;

public class AlarmCreatedEvent extends ApplicationEvent {

    private AlarmEntity alarm;

    public AlarmCreatedEvent(Object source, AlarmEntity alarm) {
        super(source);
        this.alarm = alarm;
    }

    public AlarmEntity getAlarm() {return alarm; }
}
