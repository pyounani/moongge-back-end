package com.example.narshaback.base.projection.group;

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
