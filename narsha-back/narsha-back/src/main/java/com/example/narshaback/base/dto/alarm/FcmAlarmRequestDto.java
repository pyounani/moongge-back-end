package com.example.narshaback.base.dto.alarm;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FcmAlarmRequestDto {
    private String userId;
    private String title;

    private String body;

    @Builder
    public FcmAlarmRequestDto(String userId, String title, String body) {
        this.userId = userId;
        this.title = title;
        this.body = body;
    }
}

