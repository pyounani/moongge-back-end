package com.example.narshaback.controller;


import com.example.narshaback.base.code.ResponseCode;
import com.example.narshaback.base.dto.alarm.FcmAlarmRequestDto;
import com.example.narshaback.base.dto.alarm.FcmTokenRequestDTO;
import com.example.narshaback.base.dto.response.ResponseDTO;
import com.example.narshaback.service.AlarmService;
import com.example.narshaback.service.FcmAlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class FCMAlarmApiController {

    private final FcmAlarmService fcmAlarmService;

    @PostMapping()
    public ResponseEntity<ResponseDTO> sendAlarmByToken(@RequestBody FcmAlarmRequestDto fcmAlarmRequestDto) {
        String res = fcmAlarmService.sendAlarmByToken(fcmAlarmRequestDto);

        return ResponseEntity
                .status(ResponseCode.SUCCESS_SAVE_FCM_TOKEN.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_SAVE_FCM_TOKEN, res));
    }
}

