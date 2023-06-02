package com.example.narshaback.projection.post;

import com.example.narshaback.entity.UserEntity;

import java.time.LocalDateTime;

public interface GetPostDetail {

    Integer getId();
    String getContent();
    String getImageArray();
    LocalDateTime getCreateAt();
    UserEntity getWriter();

}
