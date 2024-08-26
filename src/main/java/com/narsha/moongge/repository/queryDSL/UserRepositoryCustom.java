package com.narsha.moongge.repository.queryDSL;

import com.narsha.moongge.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryCustom {
    Optional<String> findBadgeListByUserId(String userId);
    boolean existsByGroupCodeAndUserId(String groupCode, String userId);
    List<UserEntity> getUsersInGroupExcept(String groupCode, String userId);
}
