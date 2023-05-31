package com.example.narshaback.projection.like;

import com.example.narshaback.entity.UserEntity;

public interface GetLikeList {
    UserEntity getLikeUser();

    interface UserEntity{
        String getUserId();
    }
}
