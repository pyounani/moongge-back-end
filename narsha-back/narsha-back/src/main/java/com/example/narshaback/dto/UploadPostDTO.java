package com.example.narshaback.dto;

import com.example.narshaback.entity.GroupEntity;
import com.example.narshaback.entity.UserEntity;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadPostDTO {
    @NotNull
    private String groupId; // 그룹 id

    @NotNull
    private String writer; // 작성자(userId)

    @NotNull
    private String imageArray; // 이미지 목록(이미지 링크 배열)

    @NotNull
    private String content; // 작성 내용

}
