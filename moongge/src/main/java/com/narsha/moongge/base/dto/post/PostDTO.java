package com.narsha.moongge.base.dto.post;

import com.narsha.moongge.entity.PostEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostDTO {

    private Integer postId;
    private String groupCode;
    private String writer;
    private String content;
    private String imageArray;
    private LocalDateTime createAt;

    public static PostDTO mapToPostDTO(PostEntity post) {
        return PostDTO.builder()
                .postId(post.getPostId())
                .groupCode(post.getGroupCode().getGroupCode())
                .writer(post.getUser().getUserId())
                .content(post.getContent())
                .imageArray(post.getImageArray())
                .createAt(post.getCreateAt())
                .build();
    }
}
