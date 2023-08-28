package com.example.narshaback.base.projection.alarm;

import com.example.narshaback.entity.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

public interface GetAlarmList {

    UserEntity getUserId();
    interface UserEntity{
        String getUserId();
    }

    PostEntity getPostId();

    interface  PostEntity{
        Integer getPostId();
    }

    Integer getAlarmId();

    String getActionType();

    LocalDateTime getCreatedAt();


}
