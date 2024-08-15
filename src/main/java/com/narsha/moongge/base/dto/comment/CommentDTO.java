package com.narsha.moongge.base.dto.comment;

import com.narsha.moongge.entity.CommentEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentDTO {

    private Integer commentId;
    private String content;
    private LocalDateTime createAt;
    private String writer;
    private String profileImage;
    private String nickname;

    public static CommentDTO mapToCommentDTO(CommentEntity comment) {
        return CommentDTO.builder()
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .createAt(comment.getCreateAt())
                .writer(comment.getUser().getUserId())
                .profileImage(comment.getUser().getProfileImage())
                .nickname(comment.getUser().getNickname())
                .build();
    }
}
