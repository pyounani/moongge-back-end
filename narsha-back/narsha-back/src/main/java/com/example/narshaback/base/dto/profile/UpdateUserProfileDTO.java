package com.example.narshaback.base.dto.profile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserProfileDTO {

    private Integer userGroupId; // 유저-그룹 id

    private String profileImage; // 프로필 이미지

    private String birth; // 생일

    private String nikname; // 닉네임

    private String intro; // 소개
}
