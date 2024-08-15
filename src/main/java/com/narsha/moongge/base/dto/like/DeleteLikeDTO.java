package com.narsha.moongge.base.dto.like;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeleteLikeDTO {

    @NotEmpty(message = "userId를 입력해주세요.")
    private String userId;
    @NotEmpty(message = "groupCode를 입력해주세요.")
    private String groupCode;
    @NotNull(message = "postId를 입력해주세요.")
    private Integer postId;
}