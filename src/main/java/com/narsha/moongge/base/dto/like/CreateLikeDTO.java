package com.narsha.moongge.base.dto.like;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateLikeDTO {

    @NotEmpty(message = "userId를 입력해주세요.")
    private String userId;
    @NotNull(message = "postId를 입력해주세요.")
    private Integer postId;
}