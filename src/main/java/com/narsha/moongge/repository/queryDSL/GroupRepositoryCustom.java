package com.narsha.moongge.repository.queryDSL;

import com.narsha.moongge.entity.GroupEntity;

import java.util.Optional;

public interface GroupRepositoryCustom {
    void clearGroupForUsers(GroupEntity group);
}
