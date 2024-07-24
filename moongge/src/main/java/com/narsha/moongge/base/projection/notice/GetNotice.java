package com.narsha.moongge.base.projection.notice;

import com.narsha.moongge.entity.UserEntity;

import java.time.LocalDateTime;

public interface GetNotice {

    Integer getNoticeId();

    String getNoticeTitle();

    String getNoticeContent();

    LocalDateTime getCreateAt();

    UserEntity getUser();

    interface UserEntity{
        String getUserId();
        String getUserName();
    }

}