package com.narsha.moongge.base.projection.like;

import com.narsha.moongge.entity.UserEntity;

public interface GetLikeList {

    Integer getLikeId();
    UserEntity getUserId();

    interface UserEntity{
        String getUserId();

        String getUserName();

        String getProfileImage();

    }
}
