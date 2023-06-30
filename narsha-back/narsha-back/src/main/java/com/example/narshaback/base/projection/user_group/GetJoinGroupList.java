package com.example.narshaback.base.projection.user_group;

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
