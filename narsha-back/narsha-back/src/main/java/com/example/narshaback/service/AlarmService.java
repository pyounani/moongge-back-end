package com.example.narshaback.service;

import com.example.narshaback.base.projection.alarm.GetAlarmList;

import java.util.List;

public interface AlarmService {

    List<GetAlarmList> getAlarmList(String userId, String groupCode);

    void deleteAlarm(Integer alarmId);

}

