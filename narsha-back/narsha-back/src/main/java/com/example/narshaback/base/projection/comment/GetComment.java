package com.example.narshaback.base.projection.comment;

import com.example.narshaback.base.projection.like.GetLikeList;


import java.time.LocalDateTime;

public interface GetComment {
    String getContent();
    LocalDateTime getCreateAt();

    GetLikeList.UserEntity getUserId();

    interface UserEntity{
        String getUserId();


    }

}
