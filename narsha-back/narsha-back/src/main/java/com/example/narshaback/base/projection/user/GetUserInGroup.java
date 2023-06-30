package com.example.narshaback.base.projection.user;

import com.example.narshaback.entity.UserEntity;

public interface GetUserInGroup {
    UserEntity getUserId();

    interface UserEntity{
        String getUserId();
        String getUserName();
        String getPassword();
    }
}
