package com.example.narshaback.base.projection.like;

import com.example.narshaback.entity.GroupEntity;

public interface GetLikeList {
    GroupEntity getGroupCode();

    interface GroupEntity{
        String getGroupCode();
        String createAt();
    }
}
