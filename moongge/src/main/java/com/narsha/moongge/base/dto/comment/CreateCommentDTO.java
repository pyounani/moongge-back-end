package com.narsha.moongge.base.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentDTO {

    @NotNull
    private Integer postId;

    private String userId;

    private String groupCode;

    @NotNull
    private String content;
}