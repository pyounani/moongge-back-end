package com.example.narshaback.projection.user;

import com.example.narshaback.entity.UserEntity;

public interface GetUserInGroup {
    UserEntity getUser();

    interface UserEntity{
        String getUserId();
        String getName();
        String getPassword();
    }
}
