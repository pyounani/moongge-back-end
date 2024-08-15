package com.narsha.moongge.base.dto.post;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UploadPostDTO {

    @NotEmpty(message = "groupCode를 입력하세요.")
    private String groupCode; // 그룹 코드

    @NotEmpty(message = "writer를 입력하세요.")
    private String writer; // 작성자(userId)

    private String imageArray; // 이미지 목록(이미지 링크 배열)

    @NotEmpty(message = "content를 입력하세요.")
    private String content; // 작성 내용

}