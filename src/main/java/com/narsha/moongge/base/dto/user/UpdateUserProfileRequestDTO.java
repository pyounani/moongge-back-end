package com.narsha.moongge.base.dto.user;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserProfileRequestDTO {

    private String userId; // 유저 id

    private String birth; // 생일

    private String nickname; // 닉네임

    private String intro; // 소개
}
