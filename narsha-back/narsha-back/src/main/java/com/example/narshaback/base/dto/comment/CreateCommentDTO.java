package com.example.narshaback.base.dto.comment;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentDTO {
    @NotNull
    private Integer postId;

    private String userId;

    @NotNull
    private String content;
}
