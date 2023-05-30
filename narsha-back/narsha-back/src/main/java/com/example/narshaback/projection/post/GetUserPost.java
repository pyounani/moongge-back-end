package com.example.narshaback.projection.post;

import com.example.narshaback.entity.PostEntity;
import com.example.narshaback.entity.UserEntity;

import java.time.LocalDateTime;

public interface GetUserPost {

    // UserEntity getWriter();
    Integer getId();
    String getContent();
    String getImageArray();
    LocalDateTime getCreateAt();

}
