package com.example.narshaback.base.dto.alarm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FcmTokenRequestDTO {

    private String userId;
    private String fcmToken;

}
