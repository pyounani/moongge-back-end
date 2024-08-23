package com.narsha.moongge.base.dto.user;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserProfileResponseDTO {


    private String userId; // 유저 id

    private String profileImage; // 프로필 이미지

    private String birth; // 생일

    private String nickname; // 닉네임

    private String intro; // 소개
}
