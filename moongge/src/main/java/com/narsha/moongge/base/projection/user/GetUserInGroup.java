package com.narsha.moongge.base.projection.user;

import com.narsha.moongge.entity.UserEntity;

public interface GetUserInGroup {
    UserEntity getUserId();

    interface UserEntity{
        String getUserId();
        String getUserName();
        String getPassword();
    }
}
