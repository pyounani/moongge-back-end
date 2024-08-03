package com.narsha.moongge.base.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserProfileDTO {


    private String userId; // 유저 id

    private String profileImage; // 프로필 이미지

    private String birth; // 생일

    private String nikname; // 닉네임

    private String intro; // 소개
}
