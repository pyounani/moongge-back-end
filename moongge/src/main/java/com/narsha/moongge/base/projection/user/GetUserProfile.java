package com.narsha.moongge.base.projection.user;


import com.narsha.moongge.entity.GroupEntity;

public interface GetUserProfile {
    String getUserId();

    GroupEntity getGroupCode();

    String getUserName();

    String getUserType();

    String getNickName();

    String getProfileImage();

    String getBirth();

    String getIntro();

    String getBadgeList();

}
