package com.narsha.moongge.base.dto.post;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UploadPostDTO {

    @NotNull
    private String groupCode; // 그룹 코드

    @NotNull
    private String writer; // 작성자(userId)

    @NotNull
    private String imageArray; // 이미지 목록(이미지 링크 배열)

    @NotNull
    private String content; // 작성 내용

}