package com.example.narshaback.projection.user_group;

import com.example.narshaback.entity.GroupEntity;

public interface GetJoinGroupList {
    GroupEntity getGroupCode();

    interface GroupEntity{
        String getGroupCode();
        String getGroupName();
        String getSchool();
        Integer getGrade();
        Integer getGroupClass();
    }
}
