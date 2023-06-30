package com.example.narshaback.projection.user;

import com.example.narshaback.entity.UserEntity;

public interface GetUserInGroup {
    UserEntity getUserId();

    interface UserEntity{
        String getUserId();
        String getUserName();
        String getPassword();
    }
}
