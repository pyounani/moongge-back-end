package com.example.narshaback.base.dto.user;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserProfileDTO {

    private Integer id; // 프로필 고유 id

    private Integer userGroupId; // 유저-그룹 id

    private String profileImage; // 프로필 이미지

    private String birth; // 생일

    private String nikname; // 닉네임

    private String intro; // 소개

    private String badgeList; // 뱃지 리스트(string array)
}
