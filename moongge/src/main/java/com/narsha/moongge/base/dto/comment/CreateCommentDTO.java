package com.narsha.moongge.base.dto.comment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentDTO {

    @NotEmpty(message = "groupCode를 입력해주세요.")
    private String groupCode;
    @NotNull(message = "postId를 입력해주세요.")
    private Integer postId;
    @NotEmpty(message = "writer를 입력해주세요.")
    private String writer;
    @NotEmpty(message = "content를 입력해주세요.")
    private String content;
}