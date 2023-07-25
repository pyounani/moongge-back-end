package com.example.narshaback.base.projection.user;

import com.example.narshaback.entity.GroupEntity;

public interface GetUserProfile {
    String getUserId();

    GroupEntity getGroupCode();

    String getUserName();

    String getUserType();

    String getNikName();

    String getProfileImage();

    String getBirth();

    String getIntro();

    String getBadgeList();

}
