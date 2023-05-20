package com.example.narshaback.projection;

import com.example.narshaback.entity.UserEntity;

public interface GetUserInGroup {
    UserEntity getUser();

    interface UserEntity{
        String getUserId();
        String getNikname();
        String getPassword();
        String getBirth();
    }
}
