package com.example.narshaback.service;

import com.example.narshaback.base.dto.alarm.FcmAlarmRequestDto;

public interface FcmAlarmService {
    String sendAlarmByToken(FcmAlarmRequestDto fcmAlarmRequestDto);
}
