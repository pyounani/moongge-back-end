package com.example.narshaback.base.projection.notice;

import com.example.narshaback.entity.UserEntity;

import java.time.LocalDateTime;

public interface GetNotice {

    String getNoticeTitle();

    String getNoticeContent();

    LocalDateTime getCreateAt();

    UserEntity getWriter();

    interface UserEntity{
        String getUserId();
        String getName();
    }

}