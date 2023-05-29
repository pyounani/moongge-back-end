package com.example.narshaback.dto.comment;

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

    @NotNull
    private String content;
}
