package com.example.narshaback.base.projection.post;

import java.time.LocalDateTime;

public interface GetOneUserPost {

    Integer getPostId();
    String getContent();
    String getImageArray();
    LocalDateTime getCreateAt();
    GetUserPost.UserEntity getUser();

    interface UserEntity{
        String getUserId();
        String getNikname();
        String getProfileImage();
    }
}
