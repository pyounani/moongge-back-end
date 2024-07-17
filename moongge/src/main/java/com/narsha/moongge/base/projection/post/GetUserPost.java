package com.narsha.moongge.base.projection.post;

import com.narsha.moongge.entity.PostEntity;
import com.narsha.moongge.entity.UserEntity;

import java.time.LocalDateTime;

public interface GetUserPost {


    Integer getPostId();
    String getContent();
    String getImageArray();
    LocalDateTime getCreateAt();
    UserEntity getUser();

    interface UserEntity{
        String getUserId();
        String getNikname();
        String getProfileImage();
    }
}
