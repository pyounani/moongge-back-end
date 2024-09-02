package com.narsha.moongge.base.dto.like;

import com.narsha.moongge.entity.LikeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeDTO {

    private Integer likeId;
    private String writer;
    private String username;
    private String profileImage;

    public static LikeDTO mapToLikeDTO(LikeEntity like) {
        return LikeDTO.builder()
                .likeId(like.getLikeId())
                .writer(like.getUser().getUserId())
                .username(like.getUser().getUserName())
                .profileImage(like.getUser().getProfileImage())
                .build();
    }

}
