package com.narsha.moongge.base.projection.comment;

import com.narsha.moongge.base.projection.like.GetLikeList;

import java.time.LocalDateTime;

public interface GetComment {

    Integer getCommentId();
    String getContent();
    LocalDateTime getCreateAt();

    GetLikeList.UserEntity getUserId();

    interface UserEntity{
        String getUserId();

        String getProfileImage();

    }

}
