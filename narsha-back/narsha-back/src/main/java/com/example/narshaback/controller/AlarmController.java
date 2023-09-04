package com.example.narshaback.controller;

import com.example.narshaback.base.code.ResponseCode;
import com.example.narshaback.base.dto.alarm.FcmTokenRequestDTO;
import com.example.narshaback.base.dto.response.ResponseDTO;
import com.example.narshaback.base.projection.alarm.GetAlarmList;
import com.example.narshaback.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController // JSON 형태의 결과값 반환
@RequiredArgsConstructor
@RequestMapping("/api/alarm")
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping("/list")
    public ResponseEntity<ResponseDTO> getAlarmList(@RequestParam(value = "userId")String userId,
                                                    @RequestParam(value = "groupCode")String groupCode) {
        List<GetAlarmList> alarmList = alarmService.getAlarmList(userId, groupCode);
        return ResponseEntity
                .status(ResponseCode.SUCCESS_GET_ALARM_LIST.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_GET_ALARM_LIST, alarmList));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDTO> deleteAlarm(@RequestParam(value = "alarmId")Integer alarmId) {
        alarmService.deleteAlarm(alarmId);
        return ResponseEntity
                .status(ResponseCode.SUCCESS_DELETE_ALARM.getStatus().value())
                .body(new ResponseDTO(ResponseCode.SUCCESS_DELETE_ALARM, null));
    }


}
