package com.example.narshaback.projection.like;

import com.example.narshaback.entity.UserEntity;
import com.example.narshaback.entity.User_Group;

public interface GetLikeList {
    User_Group getUserGroupId();

    interface UserEntity{
        String getUserId();
        String getGroupCode();
    }
}
