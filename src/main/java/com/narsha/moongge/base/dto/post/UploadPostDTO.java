package com.narsha.moongge.base.dto.post;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UploadPostDTO {

    @NotEmpty(message = "writer를 입력하세요.")
    private String writer; // 작성자(userId)

    @NotEmpty(message = "content를 입력하세요.")
    private String content; // 작성 내용

}