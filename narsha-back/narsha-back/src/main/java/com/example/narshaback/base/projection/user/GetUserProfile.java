package com.example.narshaback.base.projection.user;

import com.example.narshaback.entity.GroupEntity;

public interface GetUserProfile {
    String getUserId();

    GroupEntity getGroupCode();

    String getUserName();

    String getNikName();

    String getProfileImage();

    String getBirth();

    String getIntro();

    String getBadgeList();

}
